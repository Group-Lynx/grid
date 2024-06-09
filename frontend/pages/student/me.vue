<template>
  <div class="flex h-full w-full flex-col">
    <!-- Student Info Pane -->
    <div class="shrink-0 basis-80">
      <!-- Student Name Avatar -->
      <div
        class="flex h-full bg-gradient-to-r from-blue-200 from-70% to-blue-50 text-blue-50"
      >
        <span class="flex basis-1/3 flex-col items-center justify-center">
          <span
            class="rounded-xl bg-blue-600 px-10 py-6 text-5xl font-extrabold duration-75 ease-out hover:shadow-lg"
            >{{ info.name }}</span
          >
          <span class="flex">
            <button
              class="mt-2 flex items-center justify-center rounded-lg px-2 py-1 text-blue-600 duration-75 ease-out hover:bg-blue-400 hover:text-blue-50"
              @click="editMyInfoDialogVisible = true"
            >
              <i class="pi pi-pen-to-square text-xl"></i>
              <span class="ml-1 text-lg">编辑信息</span>
            </button>
            <button
              class="mt-2 flex items-center justify-center rounded-lg px-2 py-1 text-red-400 duration-75 ease-out hover:bg-red-400 hover:text-red-50"
              @click="logout"
            >
              <i class="pi pi-sign-out text-xl"></i>
              <span class="ml-1 text-lg">退出登录</span>
            </button>
          </span>
        </span>

        <!-- Student Info List -->
        <div class="my-10 flex flex-col text-lg">
          <p
            class="m-1 w-fit rounded-lg bg-blue-400 px-4 py-2 duration-75 ease-out hover:bg-blue-600 hover:shadow-lg"
          >
            <b>学号：</b>{{ info.id }}
          </p>
          <p
            class="m-1 w-fit rounded-lg bg-blue-400 px-4 py-2 duration-75 ease-out hover:bg-blue-600 hover:shadow-lg"
          >
            <b>名字：</b>{{ info.name }}
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
        <Button @click="joinClass">加入</Button>
      </div>
    </Dialog>

    <!-- Edit Personal Info Dialog -->
    <Dialog
      v-model:visible="editMyInfoDialogVisible"
      modal
      header="修改个人信息"
    >
      <div class="flex flex-col items-center gap-4">
        <div class="flex items-center gap-2">
          <label for="newNameId">新名字</label>
          <InputText id="newNameId" v-model:model-value="newName" />
        </div>
        <Button @click="patchInfo">提交</Button>
      </div>
    </Dialog>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  layout: "student",
});

const cfg = useRuntimeConfig();
const apiServer = cfg.public.apiServerBase;
const studentId = useCookie<string | null>("studentId");
const toasts = useToast();

const info = useState<StuInfo>("studentInfo");
const { data, error } = await useFetch<StuInfoResp>(
  `${apiServer}/student/${studentId.value}`,
);
{
  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "未知错误",
      detail: "尝试刷新页面或重新登录",
      life: 5000,
    });
  } else {
    info.value = {
      id: data.value!.studentId,
      name: data.value!.studentName,
    };
  }
}

const classes = useState<Class[]>("studentClasses");
async function getStuClasses() {
  const { data, error } = await useFetch<StuClaResp[]>(
    `${apiServer}/student/${studentId.value}/class`,
  );
  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "未知错误",
      detail: "尝试刷新页面或重新登录",
      life: 5000,
    });
  } else {
    classes.value = [];
    for (let cla of data.value!) {
      classes.value.push({
        id: cla.classId,
        name: cla.className,
      });
    }
  }
}
await getStuClasses();

const joinClassDialogVisible = ref<boolean>(false);
const joinClassId = ref<string>("");

async function logout() {
  studentId.value = null;
  await navigateTo("/welcome");
}

async function joinClass() {
  const { error } = await useFetch(`${apiServer}/class/join`, {
    method: "POST",
    body: JSON.stringify({
      studentId: studentId.value,
      classId: joinClassId.value,
    }),
  });

  if (error.value != null) {
    let msg = "未知错误，尝试刷新页面或重新登录";
    switch (error.value.statusCode) {
      case 404:
        msg = "班级 ID 不存在";
        break;
    }
    toasts.add({
      severity: "error",
      summary: "加入失败",
      detail: msg,
      life: 5000,
    });
  } else {
    await getStuClasses();
    joinClassDialogVisible.value = false;
  }
}

const editMyInfoDialogVisible = ref<boolean>(false);
const newName = ref<string | null>();
async function patchInfo() {
  const { error } = await useFetch(`${apiServer}/student/${studentId.value}`, {
    method: "PATCH",
    body: JSON.stringify({
      studentName: newName.value,
    }),
  });

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "未知错误",
      detail: "尝试刷新页面或重新登录",
      life: 5000,
    });
  } else {
    info.value.name = newName.value!;
    editMyInfoDialogVisible.value = false;
  }
}
</script>

<style></style>
