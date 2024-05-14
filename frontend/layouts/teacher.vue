<template>
  <div class="flex h-screen w-screen">
    <nav class="flex w-80 min-w-80 max-w-80 flex-col overflow-auto bg-blue-400">
      <button
        class="m-2 h-16 bg-red-400"
        v-for="cla in classes"
        @click="classId = cla.id"
      >
        {{ cla.name }}
      </button>
    </nav>
    <main class="flex flex-grow flex-col bg-yellow-400">
      <nav class="flex h-14 bg-lime-400">
        <NuxtLink
          class="m-2 flex w-64 items-center justify-center bg-blue-400"
          v-for="nav in navList"
          :to="'/teacher' + nav.path"
        >
          {{ nav.name }}
        </NuxtLink>
      </nav>
      <div class="flex-grow bg-fuchsia-400">
        <slot />
      </div>
    </main>

    <DebugPane />
  </div>
</template>

<script lang="ts" setup>
type NavItem = {
  name: string;
  icon: string;
  path: string;
};

const teacherId = useState("teacherId");
teacherId.value = "teacherId";
const classId = useState("classId");
classId.value = "classId";

const navList = ref<NavItem[]>([
  { name: "通知", icon: "", path: "/mail" },
  { name: "班级", icon: "", path: "/member" },
]);

const classes = useState<Class[]>();
await callOnce(async () => {
  // todos.value = await $fetch(`${cfg.apiServerBase}/teacher/${teacherId}/class`);
  classes.value = [
    // prettier-ignore
    { id: "1", name: "Class 1"},
    // prettier-ignore
    { id: "2", name: "Class 2"},
    // prettier-ignore
    { id: "3", name: "Class 3"},
  ];
});

const viewing = ref<Class | null>(null);
</script>

<style></style>
