<!-- Teacher Logic -->
<template>
  <div class="flex h-full w-full items-center justify-center">
    <p class="text-9xl font-extrabold">{{ selectedClass }}</p>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  layout: "teacher",
});

const selectedClass = useState<Class>("selectedClass");
</script>

<style></style>
