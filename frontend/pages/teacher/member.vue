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
            <Button
              class="invisible group-hover:visible"
              @click="removeStudent(stu.id)"
              >移除</Button
            >
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
        <Button @click="addStudent">添加</Button>
      </div>
    </Dialog>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  layout: "teacher",
});

const cfg = useRuntimeConfig();
const apiServer = cfg.public.apiServerBase;
const teacherId = useCookie<string>("teacherId");
const toasts = useToast();

const selectedClass = useState<Class>("selectedClass");

const classInfo = useState<ClassInfo>("classInfo");
async function getClassInfo(newClass: Class) {
  const { data, error } = await useFetch<ClaInfoResp>(
    `${apiServer}/class/${newClass.id}`,
  );

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "获取班级信息失败",
      detail: "尝试刷新页面或重新登录",
      life: 5000,
    });
  } else {
    classInfo.value = {
      id: data.value!.classId,
      name: data.value!.className,
    };
  }
}
if (selectedClass.value != null) {
  getClassInfo(selectedClass.value);
}
watch(selectedClass, async (cla) => await getClassInfo(cla));

const students = useState<StuInfo[]>("students");
async function getClaStudents(newClass: Class) {
  const { data, error } = await useFetch<ClaStuResp[]>(
    `${apiServer}/class/${newClass.id}/students`,
  );

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "获取学生列表失败",
      detail: "尝试刷新页面或重新登录",
      life: 5000,
    });
  } else {
    students.value = [];
    for (let stu of data.value!) {
      students.value.push({
        id: stu.studentId,
        name: stu.studentName,
      });
    }
  }
}
if (selectedClass.value != null) {
  getClaStudents(selectedClass.value);
}
watch(selectedClass, async (cla) => await getClaStudents(cla));

const addStudentDialogVisible = ref<boolean>(false);
const addStudentId = ref<string>();
async function addStudent() {
  const { error } = await useFetch(`${apiServer}/class/join`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      classId: selectedClass.value.id,
      studentId: addStudentId.value,
    }),
  });

  if (error.value != null) {
    let msg = "尝试刷新页面或重新登录";
    switch (error.value.statusCode) {
      case 404:
        msg = "学生 ID 不存在";
        break;
      case 409:
        msg = "学生已在班级中";
        break;
    }
    toasts.add({
      severity: "error",
      summary: "添加学生失败",
      detail: msg,
      life: 5000,
    });
  } else {
    await getClaStudents(selectedClass.value);
    addStudentId.value = undefined;
    addStudentDialogVisible.value = false;
  }
}

async function removeStudent(stuId: string) {
  const { error } = await useFetch(`${apiServer}/class/leave`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      classId: selectedClass.value.id,
      studentId: stuId,
    }),
  });

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "移除学生失败",
      detail: "尝试刷新页面或重新登录",
      life: 5000,
    });
  } else {
    if (selectedClass.value != null) {
      await getClaStudents(selectedClass.value);
    }
  }
}
</script>
