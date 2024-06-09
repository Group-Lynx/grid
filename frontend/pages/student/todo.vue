<template>
  <div class="flex h-full">
    <!-- Student Todo List -->
    <div
      class="flex basis-1/2 flex-col items-center overflow-auto bg-blue-200/30"
    >
      <div
        class="my-3 flex w-3/4 items-center rounded-md bg-blue-300 duration-75 ease-out hover:-translate-y-0.5 hover:shadow-lg"
      >
        <button
          class="flex grow items-center justify-between"
          @click="newTodoDialogVisible = true"
        >
          <p class="m-4 text-xl font-bold">添加待办</p>
          <i class="pi pi-window-maximize mr-4 text-2xl"></i>
        </button>
      </div>

      <div
        v-for="todo in todos"
        class="my-3 flex w-3/4 items-center justify-between rounded-md bg-blue-300 duration-75 ease-out hover:-translate-y-0.5 hover:shadow-lg"
      >
        <!-- Student Todo Item -->
        <button class="flex grow" @click="viewing = todo">
          <p class="m-4 text-xl font-bold">
            {{ todo.title }}
          </p>
        </button>
        <span class="m-4 flex justify-center gap-2">
          <button @click="patchTodo(todo, { display: !todo.display })">
            <i class="pi pi-eye text-xl" v-if="todo.display"></i>
            <i class="pi pi-eye-slash text-xl" v-else></i>
          </button>
          <Checkbox
            @change="patchTodo(todo, { done: todo.done })"
            v-model="todo.done"
            binary
          />
        </span>
      </div>
    </div>

    <!-- Student Todo Detail -->
    <div class="basis-1/2 overflow-auto" :class="viewing ? '' : 'bg-slate-200'">
      <div v-if="viewing" class="flex flex-col items-center">
        <div class="flex w-full items-center justify-between bg-blue-200">
          <p class="m-4 text-2xl font-bold">{{ viewing.title }}</p>
          <span class="m-4 flex items-center justify-center gap-4">
            <p>截止于：{{ formatDateTime(new Date(viewing.due)) }}</p>
            <Button @click="editTodo">编辑</Button>
            <Button @click="deleteTodo" severity="danger">删除</Button>
          </span>
        </div>

        <p class="w-full items-start p-4">{{ viewing.detail }}</p>
      </div>
    </div>

    <Dialog v-model:visible="newTodoDialogVisible" modal header="添加待办">
      <div class="flex flex-col items-center gap-4">
        <div class="flex items-center gap-2">
          <label for="newTodoTitleId">待办标题</label>
          <InputText id="newTodoTitleId" v-model="newTodoTitle" />
        </div>

        <div class="flex items-center gap-2">
          <label for="newTodoDueId">截止日期</label>
          <Calendar id="newTodoDueId" v-model="newTodoDue" />
        </div>

        <div class="flex flex-col items-center gap-2">
          <label for="newTodoDetailId">待办详情</label>
          <Textarea id="newTodoDetailId" v-model="newTodoDetail" />
        </div>
        <Button
          @click="addTodo"
          :disabled="
            newTodoTitle == null || newTodoDetail == null || newTodoDue == null
          "
          >添加</Button
        >
      </div>
    </Dialog>

    <Dialog v-model:visible="editTodoDialogVisible" modal header="编辑待办">
      <div class="flex flex-col items-center gap-4">
        <div class="flex items-center gap-2">
          <label for="viewingTodoTitleId">待办标题</label>
          <InputText id="viewingTodoTitleId" v-model="editTodoTitle" />
        </div>

        <div class="flex items-center gap-2">
          <label for="viewingTodoDueId">截止日期</label>
          <Calendar id="viewingTodoDueId" v-model="editTodoDue" />
        </div>

        <div class="flex flex-col items-center gap-2">
          <label for="viewingTodoDetailId">待办详情</label>
          <Textarea id="viewingTodoDetailId" v-model="editTodoDetail" />
        </div>
        <Button
          @click="
            patchTodo(viewing!, {
              title: editTodoTitle,
              detail: editTodoDetail,
              due: editTodoDue,
              done: viewing!.done,
              display: viewing!.display,
            })
          "
          >确认</Button
        >
      </div>
    </Dialog>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  layout: "student",
});

// TODO: dialog fields null button disable

const viewing = ref<Todo | null>(null);
const cfg = useRuntimeConfig();
const apiServer = cfg.public.apiServerBase;
const studentId = useCookie<string | null>("studentId");
const toasts = useToast();

const todos = useState<Todo[]>("todos");
async function getStuTodos() {
  const { data, error } = await useFetch<StuTodoResp[]>(
    `${apiServer}/student/${studentId.value}/todo`,
  );

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "未知错误",
      detail: "尝试刷新页面或重新登录",
      life: 5000,
    });
  } else {
    todos.value = [];
    for (let todo of data.value!) {
      todos.value.push({
        id: todo.todoId,
        title: todo.title,
        detail: todo.detail,
        done: todo.done,
        due: todo.due,
        display: todo.display,
      });
    }
    console.log(todos.value);
  }
}
await getStuTodos();

const newTodoDialogVisible = ref<boolean>(false);
const newTodoTitle = ref<string>();
const newTodoDetail = ref<string>();
const newTodoDue = ref<Date>();

async function addTodo() {
  const { error } = await useFetch(
    `${apiServer}/student/${studentId.value}/todo`,
    {
      method: "POST",
      body: JSON.stringify({
        title: newTodoTitle.value,
        detail: newTodoDetail.value,
        due: newTodoDue.value,
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
    await getStuTodos();
    newTodoDialogVisible.value = false;
    newTodoTitle.value = undefined;
    newTodoDetail.value = undefined;
    newTodoDue.value = undefined;
  }
}

async function patchTodo(todo: Todo, obj: StuTodoReq) {
  console.log(todo, obj);
  const { error } = await useFetch(
    `${apiServer}/student/${studentId.value}/todo/${todo.id}`,
    {
      method: "PATCH",
      body: JSON.stringify({
        title: obj.title ?? todo.title,
        detail: obj.detail ?? todo.detail,
        due: obj.due ?? todo.due,
        done: obj.done ?? todo.done,
        display: obj.display ?? todo.display,
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
    await getStuTodos();
    editTodoDialogVisible.value = false;
    viewing.value = null;
  }
}

async function deleteTodo() {
  if (viewing.value) {
    const { error } = await useFetch(
      `${apiServer}/student/${studentId.value}/todo/${viewing.value.id}`,
      {
        method: "DELETE",
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
      await getStuTodos();
      viewing.value = null;
    }
  }
}

function formatDateTime(date: Date): string {
  return `${date.getFullYear()} 年 ${date.getMonth() + 1} 月 ${date.getDate()} 日`;
}

// TODO: edit todo
const editTodoDialogVisible = ref<boolean>(false);
const editTodoTitle = ref<string>();
const editTodoDetail = ref<string>();
const editTodoDue = ref<Date>();
function editTodo() {
  if (viewing.value) {
    editTodoTitle.value = viewing.value.title;
    editTodoDetail.value = viewing.value.detail;
    editTodoDue.value = new Date(viewing.value.due);
    editTodoDialogVisible.value = true;
  }
}
</script>

<style></style>
