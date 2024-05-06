<!-- Student Logic -->
<template>
  <div class="flex w-screen h-screen">
    <nav class="bg-blue-400 w-20 min-w-20 max-w-20">
      <!-- Nav Items -->
      <NuxtLink v-for="item in navList" :to="'/student' + item.path">
        <div class="flex items-center justify-center h-16 m-2 bg-blue-200">
          {{ item.name }}
        </div>
      </NuxtLink>
    </nav>

    <!-- Main Content -->
    <main class="bg-fuchsia-400 w-full">
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

const navList = ref<NavItem[]>([
  { name: "日程", icon: "", path: "/calendar" },
  { name: "待办", icon: "", path: "/todo" },
  { name: "通知", icon: "", path: "/mail" },
  { name: "个人", icon: "", path: "/me" },
]);
</script>

<style></style>
