<!-- Student Logic -->
<template>
  <div class="flex h-screen w-screen">
    <nav
      class="z-50 w-20 min-w-20 max-w-20 overflow-auto bg-blue-400 shadow-xl shadow-blue-500"
    >
      <!-- Nav Items -->
      <NuxtLink v-for="item in navList" :to="'/student' + item.path">
        <div
          class="group relative m-2 flex h-16 items-center justify-center bg-blue-100 text-lg text-blue-800 duration-75 ease-out hover:translate-x-0.5 hover:rounded-md hover:shadow-lg"
          :class="
            `/student${item.path}` === $route.path
              ? 'rounded-xl shadow-lg'
              : 'rounded-3xl'
          "
        >
          <span
            class="absolute right-0 text-nowrap text-xl opacity-0 duration-75 ease-out group-hover:right-1/2 group-hover:translate-x-1/2 group-hover:opacity-100"
          >
            {{ item.name }}
          </span>
          <span
            :class="item.icon"
            class="absolute right-1/2 translate-x-1/2 text-3xl opacity-100 duration-75 ease-out group-hover:left-0 group-hover:opacity-0"
          ></span>
        </div>
      </NuxtLink>
    </nav>

    <!-- Main Content -->
    <main class="flex-grow bg-slate-50">
      <slot />
    </main>
  </div>
</template>

<script lang="ts" setup>
type NavItem = {
  name: string;
  icon: string;
  path: string;
};
const cfg = useRuntimeConfig();
const apiServer = cfg.public.apiServerBase;
const studentId = useCookie<string | null>("studentId");
const toasts = useToast();

if (studentId.value == null) {
  await navigateTo("/welcome");
}

const navList = ref<NavItem[]>([
  { name: "日程", icon: "pi pi-calendar", path: "/calendar" },
  { name: "待办", icon: "pi pi-check-square", path: "/todo" },
  { name: "通知", icon: "pi pi-inbox", path: "/mail" },
  { name: "个人", icon: "pi pi-user", path: "/me" },
]);

const todos = useState<Todo[]>("todos");
{
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
  }
}
</script>

<style></style>
