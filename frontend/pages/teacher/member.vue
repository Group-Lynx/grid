<template>
  <div>班级详情</div>
</template>

<script lang="ts" setup>
definePageMeta({
  layout: "teacher",
});

const classInfo = useState<ClassInfo>("classInfo");
const selectedClass = useState<Class>("selectedClass");
</script>

<style></style>
