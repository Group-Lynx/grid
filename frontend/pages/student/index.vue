<template>
  <div class="flex h-full w-full items-center justify-center">
    <p class="text-8xl font-extrabold text-gray-400">
      在左侧选择一个导航标签吧~
    </p>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  layout: "student",
});

const toasts = useToast();
onMounted(async () => {
  toasts.add({
    severity: "success",
    summary: "登录成功",
    life: 5000,
  });
});
</script>

<style></style>
