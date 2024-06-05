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

const navList = ref<NavItem[]>([
  { name: "日程", icon: "pi pi-calendar", path: "/calendar" },
  { name: "待办", icon: "pi pi-check-square", path: "/todo" },
  { name: "通知", icon: "pi pi-inbox", path: "/mail" },
  { name: "个人", icon: "pi pi-user", path: "/me" },
]);

const studentId = useState<string>("studentId");

// FIXME: error handling & replace with real data
const todos = useState<Todo[]>("todos");

// FIXME: error handling & replace with real data
const mails = useState<Mail[]>("mails");
await callOnce(async () => {
  // mails.value = await $fetch(`${cfg.apiServerBase}/student/${studentId}/mail`);
  mails.value = [
    // prettier-ignore
    { id: "1", title: "Mail 1", detail: "Detail 1", read: false, },
    // prettier-ignore
    { id: "2", title: "Mail 2", detail: "Detail 2", read: true, },
    // prettier-ignore
    { id: "3", title: "Mail 3", detail: "Detail 3", read: false, },
  ];
});
</script>

<style></style>
