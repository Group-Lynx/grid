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
      <span class="m-1">
        <Button icon="pi pi-plus" @click="addEventDialogVisible = true" />
      </span>
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
    <Dialog
      header="添加日程"
      v-model:visible="addEventDialogVisible"
      modal
    ></Dialog>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  layout: "student",
});

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
// FIXME: This is fake data
events.value = [];
for (let i = -2; i < 9; i++) {
  for (let j = 0; j < 5; j++) {
    events.value.push({
      id: `event${i}${j}`,
      name: `Event ${i}${j}`,
      date: new Date(
        lastMonday(new Date()).setDate(lastMonday(new Date()).getDate() + i),
      ),
      location: "Location",
      start: i,
      end: i + j,
    });
  }
}
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
// FIXME: This is fake data
todos.value = [];
for (let i = -2; i < 9; i++) {
  for (let j = 0; j < 5; j++) {
    todos.value.push({
      id: `todo${i}${j}`,
      title: `Todo ${i}${j}`,
      due: new Date(
        lastMonday(new Date()).setDate(lastMonday(new Date()).getDate() + i),
      ),
      detail: "Detail".repeat(j),
      display: true,
      done: false,
    });
  }
}
const visibleTodo = computed<Todo[][]>(() => {
  let result: Todo[][] = [];
  for (let offset = 0; offset < 7; offset++) {
    const date = new Date(
      new Date(monday.value).setDate(monday.value.getDate() + offset),
    );

    const dayTodos = todos.value.filter((todo) => {
      return (
        todo.due.getFullYear() === date.getFullYear() &&
        todo.due.getMonth() === date.getMonth() &&
        todo.due.getDate() === date.getDate() &&
        todo.display &&
        !todo.done
      );
    });

    result[offset] = dayTodos;
  }

  return result;
});

const addEventDialogVisible = ref<boolean>(false);
</script>

<style></style>
