<template>
  <div class="flex h-screen w-screen">
    <!-- Class List Nav -->
    <nav
      class="z-50 flex w-80 min-w-80 max-w-80 flex-col overflow-auto bg-blue-400 shadow-xl shadow-blue-500"
    >
      <button
        class="m-2 h-16 rounded-md bg-blue-200 duration-75 ease-out hover:translate-x-0.5 hover:shadow-lg"
        v-for="cla in classes"
        @click="selectedClass = cla"
      >
        {{ cla.name }}
      </button>
    </nav>

    <!-- Main Content -->
    <main class="flex h-full w-full flex-col">
      <!-- Main Nav -->
      <nav class="flex h-14 shrink-0 bg-blue-200">
        <NuxtLink
          class="m-2 flex w-64 items-center justify-center rounded-md bg-blue-500 font-bold text-blue-50 duration-75 ease-out hover:translate-y-0.5 hover:shadow-lg"
          v-for="nav in navList"
          :to="'/teacher' + nav.path"
        >
          {{ nav.name }}
        </NuxtLink>
      </nav>

      <!-- Actual Content -->
      <div class="h-full w-full overflow-auto">
        <slot />
      </div>
    </main>
  </div>
</template>

<script lang="ts" setup>
type NavItem = {
  name: string;
  icon: string;
  path: string;
};

const teacherId = useState<string>("teacherId");

const navList = ref<NavItem[]>([
  { name: "通知", icon: "", path: "/mail" },
  { name: "班级", icon: "", path: "/member" },
]);

const classes = useState<Class[]>("classes");
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

const selectedClass = useState<Class>("selectedClass");
watch(selectedClass, (newClass, oldClass) => {
  // FIXME: make network request to fetch class data
  console.log("selectedClass", newClass, oldClass);
});
</script>

<style></style>
