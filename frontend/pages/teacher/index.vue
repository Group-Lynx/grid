<!-- Teacher Logic -->
<template>
  <div class="flex h-full w-full items-center justify-center">
    <p class="text-9xl font-extrabold">{{ classId }}</p>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  layout: "teacher",
});

const classId = useState("classId");
</script>

<style></style>
