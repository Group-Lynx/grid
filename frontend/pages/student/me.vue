<template>
  <div class="flex h-full w-full flex-col">
    <!-- Student Info Pane -->
    <div class="shrink-0 basis-80">
      <!-- Student Name Avatar -->
      <div
        class="flex h-full bg-gradient-to-r from-blue-200 from-70% to-blue-50 text-blue-50"
      >
        <span
          class="flex basis-1/3 items-center justify-center text-5xl font-extrabold"
        >
          <span
            class="rounded-xl bg-blue-600 px-10 py-6 duration-75 ease-out hover:shadow-lg"
            >{{ info.name }}</span
          >
        </span>

        <!-- Student Info List -->
        <div class="my-10 flex flex-col text-lg">
          <p
            class="m-1 w-fit rounded-lg bg-blue-400 px-4 py-2 duration-75 ease-out hover:bg-blue-600 hover:shadow-lg"
          >
            <b>名字：</b>{{ info.name }}
          </p>
          <p
            class="m-1 w-fit rounded-lg bg-blue-400 px-4 py-2 duration-75 ease-out hover:bg-blue-600 hover:shadow-lg"
          >
            <b>学号：</b>{{ info.id }}
          </p>
        </div>
      </div>
    </div>

    <!-- Student Classes Pane -->
    <div class="m-2 flex flex-grow flex-wrap place-content-start overflow-auto">
      <!-- Join Class Cube -->
      <button
        @click="joinClassDialogVisible = true"
        class="group relative m-2 flex h-52 w-52 flex-col items-center justify-center rounded-3xl bg-blue-300 text-3xl font-bold text-blue-800 duration-75 ease-out hover:-translate-y-1 hover:rounded-md hover:shadow-xl"
        ref="joinClassRef"
      >
        加入班级
        <span
          class="pi pi-window-maximize absolute bottom-0 text-4xl text-blue-500 opacity-0 duration-75 ease-out group-hover:bottom-4 group-hover:opacity-100"
        ></span>
      </button>

      <!-- Class Cube -->
      <div
        class="relative m-2 flex h-52 w-52 flex-col items-center justify-center rounded-3xl bg-blue-300 text-blue-800 duration-75 ease-out hover:-translate-y-1 hover:rounded-md hover:shadow-xl"
        v-for="cla in classes"
      >
        <span class="text-3xl font-bold">{{ cla.name }}</span>
        <span class="absolute bottom-4 text-blue-500">{{ cla.id }}</span>
        <div
          class="group absolute flex h-full w-full items-center justify-center rounded-3xl duration-75 ease-out hover:rounded-md hover:bg-gray-400/40"
        >
          <span class="opacity-0 duration-75 ease-out group-hover:opacity-100">
            <Button>离开</Button>
          </span>
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
