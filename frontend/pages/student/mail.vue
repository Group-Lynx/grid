<template>
  <div class="flex h-full">
    <div class="flex basis-1/2 flex-col items-center overflow-auto bg-lime-400">
      <!-- Student Mail List -->
      <div
        v-for="mail in mails"
        class="my-3 flex w-3/4 items-center justify-between bg-cyan-300"
      >
        <!-- Student Mail Item -->
        <span class="grow bg-violet-400" @click="viewing = mail">
          <p class="m-4 text-xl font-bold">
            {{ mail.title }}
          </p>
        </span>
      </div>
    </div>

    <!-- Student Mail Detail -->
    <div class="basis-1/2 overflow-auto bg-yellow-400">
      <div v-if="viewing" class="flex flex-col items-center">
        <div class="flex w-full items-center justify-between bg-sky-400">
          <p class="m-4 text-2xl font-bold">{{ viewing.title }}</p>
          <div>
            <Button class="m-4" @click="showAlert">标记已读</Button>
            <Button class="m-4" @click="showAlert">转为待办</Button>
          </div>
        </div>

        <p class="m-4">{{ viewing.detail }}</p>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  layout: "student",
});

const mails = useState<Mail[]>("mails");
const viewing = ref<Mail | null>(null);

function showAlert() {
  alert("FIXME");
}
</script>

<style></style>
