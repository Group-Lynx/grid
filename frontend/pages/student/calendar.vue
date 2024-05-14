<template>
  <div class="flex h-full w-full flex-col">
    <div class="flex-grow bg-lime-400">死线任务区</div>
    <div class="h-16 bg-red-400">导航栏区</div>
    <div class="basis-3/5 bg-yellow-400">课程表区</div>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  layout: "student",
});
</script>

<style></style>
