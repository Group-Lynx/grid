<!-- Login & Signup Logic -->
<template>
  <div
    class="flex h-screen w-screen flex-col items-center justify-center gap-2 bg-blue-200"
  >
    <TabView>
      <TabPanel>
        <template #header>
          <span class="pi pi-book text-xl"></span>
          <span class="pl-2">学生登录</span>
        </template>
        <div class="flex flex-col items-center gap-4">
          <InputText placeholder="用户 ID" v-model:model-value="uid" />
          <Password
            :feedback="false"
            placeholder="密码"
            v-model:model-value="pswd"
          />
          <Button @click="studentLogin" :disabled="uid === '' || pswd === ''">
            <span class="pi pi-sign-in text-xl"></span>
            <span class="pl-2">登录</span>
          </Button>
        </div>
      </TabPanel>

      <TabPanel>
        <template #header>
          <span class="pi pi-book text-xl"></span>
          <span class="pl-2">学生注册</span>
        </template>
        <div class="flex flex-col items-center gap-4">
          <InputText placeholder="用户 ID" v-model:model-value="uid" />
          <Password
            :feedback="false"
            placeholder="密码"
            v-model:model-value="pswd"
          />
          <Password
            :feedback="false"
            placeholder="确认密码"
            v-model:model-value="pswdConfirm"
          />
          <Button
            @click="studentSignup"
            :disabled="pswd !== pswdConfirm || uid === '' || pswd === ''"
          >
            <span class="pi pi-user-plus text-xl"></span>
            <span class="pl-2">注册</span>
          </Button>
        </div>
      </TabPanel>

      <TabPanel>
        <template #header>
          <span class="pi pi-graduation-cap text-xl"></span>
          <span class="pl-2">教师登录</span>
        </template>
        <div class="flex flex-col items-center gap-4">
          <InputText placeholder="用户 ID" v-model:model-value="uid" />
          <Password
            :feedback="false"
            placeholder="密码"
            v-model:model-value="pswd"
          />
          <Button @click="teacherLogin" :disabled="uid === '' || pswd === ''">
            <span class="pi pi-sign-in text-xl"></span>
            <span class="pl-2">登录</span>
          </Button>
        </div>
      </TabPanel>

      <TabPanel>
        <template #header>
          <span class="pi pi-graduation-cap text-xl"></span>
          <span class="pl-2">教师注册</span>
        </template>
        <div class="flex flex-col items-center gap-4">
          <InputText placeholder="用户 ID" v-model:model-value="uid" />
          <Password
            :feedback="false"
            placeholder="密码"
            v-model:model-value="pswd"
          />
          <Password
            :feedback="false"
            placeholder="确认密码"
            v-model:model-value="pswdConfirm"
          />
          <Button
            @click="teacherSignup"
            :disabled="pswd != pswdConfirm || uid === '' || pswd === ''"
          >
            <span class="pi pi-user-plus text-xl"></span>
            <span class="pl-2">注册</span>
          </Button>
        </div>
      </TabPanel>
    </TabView>

    <button
      class="flex h-10 w-40 items-center justify-center rounded-lg bg-gray-100 font-bold text-blue-600 duration-75 ease-out hover:w-52 hover:bg-white"
      @click="navigateTo('/recover')"
    >
      找回密码
    </button>
  </div>
</template>

<script lang="ts" setup>
const cfg = useRuntimeConfig();
const apiServer = cfg.public.apiServerBase;
const toasts = useToast();

const uid = ref("");
const pswd = ref("");
const pswdConfirm = ref("");

async function studentLogin() {
  const { error } = await useFetch(`${apiServer}/student/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      studentId: uid.value,
      password: pswd.value,
    }),
  });

  if (error.value != null) {
    let err = error.value;
    let msg = "未知错误";
    switch (err?.statusCode) {
      case 404:
        msg = "ID 不存在或密码错误";
        break;
    }
    console.log("err", err);
    toasts.add({
      severity: "error",
      summary: "登录失败",
      detail: msg,
      life: 5000,
    });
    return;
  }

  const studentId = useCookie<string>("studentId");
  studentId.value = uid.value;
  await navigateTo("/student");
}

async function studentSignup() {
  const { error } = await useFetch(`${apiServer}/student/signup`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      studentId: uid.value,
      password: pswd.value,
    }),
  });

  if (error.value != null) {
    let err = error.value;
    let msg = "未知错误";
    switch (err?.statusCode) {
      case 409:
        msg = "学生 ID 冲突";
        break;
    }
    console.log("err", err);
    toasts.add({
      severity: "error",
      summary: "注册失败",
      detail: msg,
      life: 5000,
    });
    return;
  }

  toasts.add({
    severity: "success",
    summary: "注册成功",
    detail: "请登录",
    life: 5000,
  });
}

async function teacherLogin() {
  const { error } = await useFetch(`${apiServer}/teacher/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      teacherId: uid.value,
      password: pswd.value,
    }),
  });

  if (error.value != null) {
    let err = error.value;
    let msg = "未知错误";
    switch (err?.statusCode) {
      case 404:
        msg = "ID 不存在或密码错误";
        break;
    }
    console.log("err", err);
    toasts.add({
      severity: "error",
      summary: "登录失败",
      detail: msg,
      life: 5000,
    });
  } else {
    const teacherId = useCookie<string>("teacherId");
    teacherId.value = uid.value;
    await navigateTo("/teacher");
  }
}
async function teacherSignup() {
  const { error } = await useFetch(`${apiServer}/teacher/signup`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      teacherId: uid.value,
      password: pswd.value,
    }),
  });

  if (error.value != null) {
    let err = error.value;
    let msg = "未知错误";
    switch (err?.statusCode) {
      case 409:
        msg = "教师 ID 冲突";
        break;
    }
    console.log("err", err);
    toasts.add({
      severity: "error",
      summary: "注册失败",
      detail: msg,
      life: 5000,
    });
  } else {
    toasts.add({
      severity: "success",
      summary: "注册成功",
      detail: "请登录",
      life: 5000,
    });
  }
}
</script>

<style></style>
