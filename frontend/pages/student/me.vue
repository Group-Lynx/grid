<template>
  <div class="flex h-full w-full flex-col">
    <!-- Student Info Pane -->
    <div class="shrink-0 basis-80 bg-lime-400">
      <div class="flex h-full">
        <span
          class="flex basis-1/3 items-center justify-center text-5xl font-extrabold"
        >
          {{ info.name }}
        </span>
        <div class="my-10 text-lg">
          <p><b>名字：</b>{{ info.name }}</p>
          <p><b>学号：</b>{{ info.id }}</p>
        </div>
      </div>
    </div>

    <!-- Student Classes Pane -->
    <div class="m-2 flex flex-grow flex-wrap place-content-start overflow-auto">
      <!-- Join Class Cube -->
      <button
        @click="joinClassDialogVisible = true"
        class="relative m-2 flex h-52 w-52 flex-col items-center justify-center bg-emerald-400 text-3xl font-bold"
      >
        加入班级
      </button>

      <!-- Class Cube -->
      <div
        class="relative m-2 flex h-52 w-52 flex-col items-center justify-center bg-emerald-400"
        v-for="cla in classes"
      >
        <span class="text-3xl font-bold">{{ cla.name }}</span>
        <span class="absolute bottom-4 text-gray-600">{{ cla.id }}</span>
        <div
          class="group absolute flex h-full w-full items-center justify-center hover:bg-gray-400/60"
        >
          <Button class="invisible group-hover:visible">离开</Button>
        </div>
      </div>
    </div>

    <!-- Join Class Dialog -->
    <Dialog v-model:visible="joinClassDialogVisible" modal header="加入班级">
      <div class="flex flex-col items-center gap-4">
        <div class="flex items-center gap-2">
          <label for="joinClassId">班级 ID</label>
          <InputText id="joinClassId" v-model:model-value="joinClassId" />
        </div>
        <Button>加入</Button>
      </div>
    </Dialog>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  layout: "student",
});

const info = useState<StuInfo>("studentInfo");
// TODO: replace with student info
info.value = {
  id: "studentId",
  name: "学生名字",
};

const classes = useState<Class[]>("studentClasses");
// TODO: replace with real data
classes.value = [];
for (let i = 1; i <= 10; i++) {
  classes.value.push({ id: i.toString(), name: `Class ${i}` });
}

const joinClassDialogVisible = ref<boolean>(false);
const joinClassId = ref<string>("");
</script>

<style></style>
