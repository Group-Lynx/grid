<template>
  <DebugOnly>
    <div
      class="absolute bottom-10 right-10 flex flex-col items-center bg-red-600"
    >
      <div class="font-bold text-white">Debug</div>
      <div class="flex">
        <div class="m-1 bg-white" v-for="nav in navList">
          <NuxtLink :to="nav.link">{{ nav.name }}</NuxtLink>
        </div>
      </div>
    </div>
  </DebugOnly>
</template>

<script lang="ts" setup>
type NavItem = {
  link: string;
  name: string;
};
const navList = ref<NavItem[]>([
  { link: "/student", name: "学生" },
  { link: "/teacher", name: "老师" },
  { link: "/", name: "主页" },
]);
</script>
