<template>
  <div class="relative flex h-full w-full flex-col">
    <!-- Deadline Section -->
    <div class="grid grow grid-cols-7 grid-rows-1">
      <div
        class="m-1 overflow-auto duration-75 ease-out hover:bg-slate-200"
        v-for="d in [0, 1, 2, 3, 4, 5, 6]"
      >
        <div
          class="m-1 flex h-20 flex-col items-center justify-center overflow-hidden text-nowrap rounded-md bg-blue-400 duration-75 ease-out hover:-translate-y-0.5 hover:shadow-xl"
          v-for="t in visibleTodo[d]"
        >
          <span class="text-xl font-bold">{{ t.title }}</span>
          <span>{{ truncateString(t.detail) }}</span>
        </div>
      </div>
    </div>

    <!-- Calender Header -->
    <div class="grid h-16 shrink-0 grid-cols-7 grid-rows-1 bg-blue-300">
      <div
        class="flex items-center justify-center overflow-hidden text-nowrap text-2xl font-bold"
        v-for="d in thisWeek"
      >
        {{ dateToString(d) }}
      </div>
    </div>

    <!-- Calender Content -->
    <div
      class="5 grid max-h-[60%] basis-3/5 grid-flow-col grid-cols-7 grid-rows-1"
    >
      <div
        class="m-1 flex flex-col overflow-auto duration-75 ease-out hover:bg-slate-200"
        v-for="d in [0, 1, 2, 3, 4, 5, 6]"
      >
        <!-- Event Item -->
        <div
          class="m-1 flex h-20 flex-col items-center justify-center overflow-hidden text-nowrap rounded-md bg-indigo-400 duration-75 ease-out hover:-translate-y-0.5 hover:shadow-xl"
          v-for="e in visibleEvent[d]"
        >
          <span class="text-xl font-bold">{{ e.name }}</span>
          <span>{{ e.location }}, {{ e.start }} ~ {{ e.end }}</span>
        </div>
      </div>
    </div>

    <!-- Bottom Tool Bar -->
    <div
      class="absolute bottom-6 left-1/2 flex -translate-x-1/2 rounded-md bg-cyan-100/50 p-2 opacity-40 duration-150 ease-out hover:opacity-100"
    >
      <span class="m-1">
        <Button
          icon="pi pi-angle-left"
          label="上周"
          @click="monday = prevMonday(monday)"
        />
      </span>

      <span class="peer m-1">
        <Button icon="pi pi-plus" @click="addEventDialogVisible = true" />
      </span>
      <span
        class="absolute left-1/2 -translate-x-1/2 -translate-y-10 rounded-md bg-cyan-100/50 p-2 text-blue-600 opacity-0 duration-75 ease-out peer-hover:-translate-y-14 peer-hover:opacity-100"
        >添加日程</span
      >

      <span class="m-1">
        <Button
          icon="pi pi-angle-right"
          label="下周"
          iconPos="right"
          @click="monday = nextMonday(monday)"
        />
      </span>
    </div>

    <!-- Add New Event Dialog -->
    <Dialog header="添加日程" v-model:visible="addEventDialogVisible" modal>
      <div class="flex flex-col items-center gap-4">
        <div class="flex items-center gap-2">
          <label for="eventNameId">名称</label>
          <InputText id="eventNameId" v-model:model-value="newEventName" />
        </div>
        <div class="flex items-center gap-2">
          <label for="eventLocationId">地点</label>
          <InputText
            id="eventLocationId"
            v-model:model-value="newEventLocation"
          />
        </div>
        <div class="flex items-center gap-2">
          <label for="eventLocationId">日期</label>
          <Calendar id="eventLocationId" v-model:model-value="newEventDate" />
        </div>
        <div class="flex items-center gap-2">
          <label for="eventLocationId">开始</label>
          <InputNumber
            id="eventLocationId"
            v-model:model-value="newEventStart"
            :min="1"
            :max="24"
            placeholder="小时"
          />
        </div>
        <div class="flex items-center gap-2">
          <label for="eventLocationId">结束</label>
          <InputNumber
            id="eventLocationId"
            v-model:model-value="newEventEnd"
            :min="1"
            :max="24"
            placeholder="小时"
          />
        </div>

        <Button @click="addNewEvent">添加</Button>
      </div>
    </Dialog>
  </div>
</template>

<script lang="ts" setup>
import type Calendar from "primevue/calendar";
import InputNumber from "primevue/inputnumber";

definePageMeta({
  layout: "student",
});

const cfg = useRuntimeConfig();
const apiServer = cfg.public.apiServerBase;
const studentId = useCookie<string | null>("studentId");
const toasts = useToast();

function truncateString(str: string, length = 10) {
  if (str.length > length) {
    return str.substring(0, length) + "...";
  }
  return str;
}
// https://stackoverflow.com/questions/4156434/javascript-get-the-first-day-of-the-week-from-current-date
function lastMonday(d: Date): Date {
  d = new Date(d);
  let day = d.getDay();
  let diff = d.getDate() - day + (day == 0 ? -6 : 1); // adjust when day is sunday
  return new Date(d.setDate(diff));
}

function prevMonday(d: Date): Date {
  d = new Date(d);
  let day = d.getDay();
  let diff = d.getDate() - day - 6;
  return new Date(d.setDate(diff));
}

function nextMonday(d: Date): Date {
  d = new Date(d);
  let day = d.getDay();
  let diff = d.getDate() + (8 - day);
  return new Date(d.setDate(diff));
}

function dateToString(d: Date): string {
  return `${d.getFullYear()}-${d.getMonth() + 1}-${d.getDate()}`;
}

// Date Support
const monday = ref<Date>(lastMonday(new Date()));
const thisWeek = computed<Date[]>(() => {
  let result: Date[] = [];
  for (let i = 0; i < 7; i++) {
    result.push(
      new Date(new Date(monday.value).setDate(monday.value.getDate() + i)),
    );
  }
  return result;
});

// Event Scripts
const events = useState<StuEvent[]>("events");
async function getStuEvents() {
  const { data, error } = await useFetch<StuEventResp[]>(
    `${apiServer}/student/${studentId.value}/event`,
  );
  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "未知错误",
      detail: "尝试刷新页面或重新登录",
      life: 5000,
    });
  } else {
    events.value = [];
    for (let event of data.value!) {
      events.value.push({
        id: event.id,
        name: event.name,
        date: new Date(event.date),
        location: event.location,
        start: event.start_hour,
        end: event.end_hour,
      });
    }
  }
}
await getStuEvents();

const visibleEvent = computed<StuEvent[][]>(() => {
  let result: StuEvent[][] = [];
  for (let offset = 0; offset < 7; offset++) {
    const date = new Date(
      new Date(monday.value).setDate(monday.value.getDate() + offset),
    );

    const dayEvents = events.value.filter((event) => {
      const eventDate = new Date(event.date);
      return (
        eventDate.getFullYear() === date.getFullYear() &&
        eventDate.getMonth() === date.getMonth() &&
        eventDate.getDate() === date.getDate()
      );
    });

    result[offset] = dayEvents;
  }

  return result;
});

// Todo Scripts
const todos = useState<Todo[]>("todos");
if (todos.value == null) {
  todos.value = [];
}

const visibleTodo = computed<Todo[][]>(() => {
  let result: Todo[][] = [];
  for (let offset = 0; offset < 7; offset++) {
    const date = new Date(
      new Date(monday.value).setDate(monday.value.getDate() + offset),
    );

    const dayTodos = todos.value.filter((todo) => {
      let due = new Date(todo.due);
      return (
        due.getFullYear() === date.getFullYear() &&
        due.getMonth() === date.getMonth() &&
        due.getDate() === date.getDate() &&
        todo.display &&
        !todo.done
      );
    });

    result[offset] = dayTodos;
  }

  return result;
});

const addEventDialogVisible = ref<boolean>(false);
const newEventName = ref<string>();
const newEventLocation = ref<string>();
const newEventDate = ref<Date>();
const newEventStart = ref<number>();
const newEventEnd = ref<number>();

async function addNewEvent() {
  const { data, error } = await useFetch<StuEventResp>(
    `${apiServer}/student/${studentId.value}/event`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name: newEventName.value,
        location: newEventLocation.value,
        date: newEventDate.value,
        start: newEventStart.value,
        end: newEventEnd.value,
      }),
    },
  );

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "未知错误",
      detail: "尝试刷新页面或重新登录",
      life: 5000,
    });
  } else {
    events.value.push({
      id: data.value!.id,
      name: data.value!.name,
      date: new Date(data.value!.date),
      location: data.value!.location,
      start: data.value!.start_hour,
      end: data.value!.end_hour,
    });
    addEventDialogVisible.value = false;

    newEventName.value = undefined;
    newEventLocation.value = undefined;
    newEventDate.value = undefined;
    newEventStart.value = undefined;
    newEventEnd.value = undefined;

    await getStuEvents();
  }
}
</script>

<style></style>
