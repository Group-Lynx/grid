<template>
  <div class="flex h-full w-full">
    <!-- Mail List -->
    <div
      class="flex basis-1/2 flex-col items-center overflow-auto bg-orange-400"
    >
      <!-- A Mail Item -->
      <div
        v-for="mail in mails"
        class="my-3 flex w-3/4 items-center justify-between bg-cyan-300"
      >
        <button class="flex grow bg-violet-400" @click="viewing = mail">
          <p class="m-4 text-xl font-bold">
            {{ mail.title }}
          </p>
        </button>
      </div>
    </div>

    <!-- Mail Detail -->
    <div class="h-full basis-1/2 overflow-auto bg-teal-400">
      <div v-if="viewing" class="flex flex-col items-center">
        <div class="flex w-full items-center justify-between bg-sky-400">
          <p class="m-4 text-2xl font-bold">{{ viewing.title }}</p>
          <div>
            <Button class="m-4" @click="showAlert">发布</Button>
          </div>
        </div>

        <p class="m-4">{{ viewing.detail }}</p>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  layout: "teacher",
});

const mails = useState<ClassMail[]>("classMails");
// TODO: replace with real data
mails.value = [];
for (let i = 1; i <= 10; i++) {
  mails.value.push({
    id: i.toString(),
    title: `Mail ${i}`,
    detail: `Mail Content ${i}`,
    draft: false,
  });
}

const selectedClass = useState<Class>("selectedClass");
// FIXME: make network request to fetch class data & clear mail selection
watch(selectedClass, (newClass, oldClass) => {
  console.log("selectedClass", newClass, oldClass);
});

const viewing = ref<ClassMail | null>(null);

function showAlert() {
  alert("FIXME");
}
</script>
