<template>
  <div class="flex h-screen w-screen">
    <!-- Class List Nav -->
    <nav
      class="z-50 flex h-full w-80 min-w-80 max-w-80 flex-col justify-between bg-blue-400 shadow-xl shadow-blue-500"
    >
      <span
        class="flex h-[90%] w-full shrink-0 grow-0 flex-col items-center overflow-auto"
      >
        <button
          class="group m-2 flex h-16 min-h-16 w-11/12 items-center justify-center rounded-md bg-blue-200 duration-75 ease-out hover:translate-x-0.5 hover:shadow-lg"
          v-for="cla in classes"
          @click="selectedClass = cla"
        >
          {{ cla.name }}
          <span
            class="absolute flex h-16 items-center justify-center overflow-visible bg-gray-400/50 opacity-0 delay-0 duration-75 ease-out group-hover:opacity-100 group-hover:delay-500"
          >
            {{ cla.id }}
          </span>
        </button>
      </span>

      <span class="flex grow items-center justify-center">
        <button
          class="ml-2 mr-1 flex h-[80%] grow items-center justify-center rounded-xl border-blue-600 bg-blue-200 duration-75 ease-out hover:-translate-y-0.5 hover:bg-blue-300 hover:shadow-lg"
          @click="beginEditTeacherInfo"
        >
          <i class="pi pi-user mr-1"></i>
          个人信息
        </button>
        <button
          class="ml-1 mr-2 flex h-[80%] grow items-center justify-center rounded-xl border-blue-600 bg-blue-200 duration-75 ease-out hover:-translate-y-0.5 hover:bg-blue-300 hover:shadow-lg"
          @click="addClassDialogVisible = true"
        >
          <i class="pi pi-plus mr-1"></i>
          添加班级
        </button>
      </span>
    </nav>

    <!-- Main Content -->
    <main class="flex h-full w-full flex-col">
      <!-- Main Nav -->
      <nav class="flex h-14 shrink-0 bg-blue-200">
        <NuxtLink
          class="m-2 flex w-64 items-center justify-center rounded-md bg-blue-500 font-bold text-blue-50 duration-75 ease-out hover:translate-y-0.5 hover:shadow-lg"
          v-for="nav in navList"
          :to="'/teacher' + nav.path"
        >
          {{ nav.name }}
        </NuxtLink>
      </nav>

      <!-- Actual Content -->
      <div class="h-full w-full overflow-auto">
        <slot v-if="selectedClass != null" />
        <div v-else class="flex h-full w-full items-center justify-center">
          <p class="text-7xl font-extrabold text-gray-300">
            选择一个班级以开始操作
          </p>
        </div>
      </div>
    </main>

    <Dialog v-model:visible="addClassDialogVisible" modal header="添加班级">
      <div class="flex flex-col items-center gap-4">
        <div class="flex items-center gap-2">
          <label for="addClassNameId">班级名称</label>
          <InputText id="addClassNameId" v-model:model-value="addClassName" />
        </div>
        <Button @click="addClass()">确认添加</Button>
      </div>
    </Dialog>
  </div>

  <Dialog v-model:visible="teacherInfoDialogVisible" modal header="个人信息">
    <div class="flex flex-col items-center gap-4">
      <div class="flex items-center gap-2">
        <label for="teacherId">教师 ID</label>
        <InputText id="teacherId" v-model="teacherInfo.id" readonly disabled />
      </div>

      <div class="flex items-center gap-2">
        <label for="teacherName">教师姓名</label>
        <InputText id="teacherName" v-model="newTeacherName" />
      </div>

      <span class="flex gap-4">
        <Button @click="patchTeacherInfo">提交修改</Button>
        <Button @click="logout" severity="danger">登出账户</Button>
      </span>
    </div>
  </Dialog>
</template>

<script lang="ts" setup>
type NavItem = {
  name: string;
  icon: string;
  path: string;
};

const cfg = useRuntimeConfig();
const apiServer = cfg.public.apiServerBase;
const teacherId = useCookie<string | null>("teacherId");
const toasts = useToast();

if (teacherId.value == null) {
  await navigateTo("/welcome");
}

const navList = ref<NavItem[]>([
  { name: "通知", icon: "", path: "/mail" },
  { name: "班级", icon: "", path: "/member" },
]);

const classes = useState<Class[]>("classes");
async function getClasses() {
  const { data, error } = await useFetch<TchClaResp[]>(
    `${apiServer}/teacher/${teacherId.value}/class`,
  );

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "获取班级列表失败",
      detail: "尝试刷新页面或重新登录",
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
await getClasses();

const selectedClass = useState<Class | null>("selectedClass");
watch(selectedClass, (newClass, _) => {
  toasts.add({
    severity: "info",
    summary: "班级切换",
    detail: `切换到班级 ${newClass!.name}`,
    life: 5000,
  });
});

const addClassDialogVisible = ref<boolean>(false);
const addClassName = ref<string>();
async function addClass() {
  const { error } = await useFetch(`${apiServer}/class`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      teacherId: teacherId.value,
      className: addClassName.value,
    }),
  });

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "添加班级失败",
      detail: "尝试刷新页面或重新登录",
    });
  } else {
    await getClasses();
    addClassName.value = undefined;
    addClassDialogVisible.value = false;
  }
}

const teacherInfoDialogVisible = ref<boolean>(false);
const teacherInfo = useState<TchInfo>("teacherInfo");
if (teacherInfo.value == null) {
  teacherInfo.value = {
    id: "",
    name: "",
  };
}
async function getTeacherInfo() {
  const { data, error } = await useFetch<TchInfoResp>(
    `${apiServer}/teacher/${teacherId.value}`,
  );

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "获取教师信息失败",
      detail: "尝试刷新页面或重新登录",
    });
  } else {
    teacherInfo.value = {
      id: data.value!.teacherId,
      name: data.value!.teacherName,
    };
  }
}
await getTeacherInfo();

const newTeacherName = ref<string>("");
function beginEditTeacherInfo() {
  newTeacherName.value = teacherInfo.value.name;
  teacherInfoDialogVisible.value = true;
}

async function patchTeacherInfo() {
  const { error } = await useFetch(`${apiServer}/teacher/${teacherId.value}`, {
    method: "PATCH",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      name: newTeacherName.value,
    }),
  });

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "更新教师信息失败",
      detail: "尝试刷新页面或重新登录",
    });
  } else {
    toasts.add({
      severity: "success",
      summary: "更新教师信息成功",
      detail: "刷新页面以查看更新",
    });
    await getTeacherInfo();
  }
}

const mails = useState<ClassMail[]>("classMails");
async function logout() {
  teacherId.value = null;
  await navigateTo("/welcome");
  // TODO: clear state
  classes.value = [];
  selectedClass.value = null;
  mails.value = [];
}
</script>

<style></style>
