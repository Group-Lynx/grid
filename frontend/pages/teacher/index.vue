<!-- Teacher Logic -->
<template>
  <div class="flex h-full w-full items-center justify-center">
    <p class="text-7xl font-extrabold text-gray-300">选择一个班级以开始操作</p>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  layout: "teacher",
});

const selectedClass = useState<Class>("selectedClass");
</script>

<style></style>
