<template>
  <div>
    <div
      class="m-2 flex max-w-fit flex-grow flex-wrap place-content-start overflow-auto"
    >
      <!-- Add Student Cube -->
      <button
        class="group relative m-2 flex h-48 w-48 flex-col items-center justify-center rounded-2xl bg-blue-300 text-3xl font-bold text-blue-800 duration-75 ease-out hover:-translate-y-1 hover:rounded-md hover:shadow-xl"
        @click="addStudentDialogVisible = true"
      >
        添加学生
        <span
          class="pi pi-window-maximize absolute bottom-0 text-4xl text-blue-500 opacity-0 duration-75 ease-out group-hover:bottom-4 group-hover:opacity-100"
        ></span>
      </button>

      <!-- Student Cube -->
      <div
        class="relative m-2 flex h-48 w-48 flex-col items-center justify-center rounded-2xl bg-blue-300 text-blue-800 duration-75 ease-out hover:-translate-y-1 hover:rounded-md hover:shadow-xl"
        v-for="stu in students"
      >
        <span class="text-3xl font-bold">{{ stu.name }}</span>
        <span class="absolute bottom-4 text-blue-500">{{ stu.id }}</span>
        <div
          class="group absolute flex h-full w-full items-center justify-center duration-75 ease-out hover:rounded-md hover:bg-gray-400/40"
        >
          <span class="opacity-0 duration-75 ease-out group-hover:opacity-100">
            <Button class="invisible group-hover:visible">移除</Button>
          </span>
        </div>
      </div>
    </div>

    <!-- Add Student Dialog -->
    <Dialog header="添加学生" v-model:visible="addStudentDialogVisible" modal>
      <div class="flex flex-col items-center gap-4">
        <div class="flex items-center gap-2">
          <label for="addStudentId">学生 ID</label>
          <InputText id="addStudentId" v-model:model-value="addStudentId" />
        </div>
        <Button>添加</Button>
      </div>
    </Dialog>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  layout: "teacher",
});

const classInfo = useState<ClassInfo>("classInfo");
const students = useState<StuInfo[]>("students");
students.value = [];
for (let i = 0; i < 10; i++) {
  students.value.push({
    id: `${i}`,
    name: `学生${i}`,
  });
}

const selectedClass = useState<Class>("selectedClass");

const addStudentDialogVisible = ref<boolean>(false);
const addStudentId = ref<string>("");
</script>
