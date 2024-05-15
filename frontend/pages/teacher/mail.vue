<template>
  <div class="flex h-full w-full">
    <div class="h-full basis-1/2 bg-orange-400">
      <div
        v-for="mail in mails"
        class="my-3 flex w-3/4 items-center justify-between bg-cyan-300"
      >
        <span class="grow bg-violet-400" @click="viewing = mail">
          <p class="m-4 text-xl font-bold">
            {{ mail.title }}
          </p>
        </span>
      </div>
    </div>
    <div class="h-full basis-1/2 bg-teal-400">通知细节</div>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  layout: "teacher",
});

const mails = useState<ClassMail[]>("classMails");
const selectedClass = useState<Class>("selectedClass");

const viewing = ref<Mail | null>(null);
</script>

<style></style>
