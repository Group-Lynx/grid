<template>
  <div class="flex h-full">
    <!-- Student Mail List -->
    <div
      class="flex basis-1/2 flex-col items-center overflow-auto bg-blue-200/30"
    >
      <div
        v-for="mail in mails"
        class="my-3 flex w-3/4 items-center justify-between"
      >
        <!-- Student Mail Item -->
        <button
          class="flex grow rounded-md bg-blue-300 duration-75 ease-out hover:-translate-y-0.5 hover:shadow-lg"
          @click="viewing = mail"
        >
          <p class="m-4 text-xl font-bold">
            {{ mail.title }}
          </p>
        </button>
      </div>
    </div>

    <!-- Student Mail Detail -->
    <div class="basis-1/2 overflow-auto" :class="viewing ? '' : 'bg-slate-200'">
      <div v-if="viewing" class="flex flex-col items-center">
        <div class="flex w-full items-center justify-between bg-blue-200">
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
