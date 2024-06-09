<template>
  <div
    class="flex h-screen w-screen flex-col items-center justify-center gap-2 bg-blue-200"
  >
    <TabView>
      <TabPanel>
        <template #header>
          <span class="pi pi-book text-xl"></span>
          <span class="pl-2">学生找回密码</span>
        </template>
        <div class="flex flex-col items-center gap-4">
          <InputText
            placeholder="学生 ID"
            :readonly="studentStep !== 0"
            :disabled="studentStep !== 0"
            v-model:model-value="uid"
          />
          <InputText
            placeholder="验证码"
            v-model:model-value="studentCode"
            v-if="studentStep === 1"
          />
          <Password
            placeholder="新密码"
            v-if="studentStep === 2"
            v-model="pswd"
          />
          <Password
            placeholder="确认密码"
            v-if="studentStep === 2"
            v-model="pswdConfirm"
          />
          <span class="flex gap-4">
            <Button
              @click="studentRecover"
              v-if="studentStep === 0"
              :disabled="uid == null || uid.length == 0"
            >
              <span class="pi pi-envelope text-xl"></span>
              <span class="pl-2">发送邮件</span>
            </Button>
            <Button
              @click="studentVerify"
              v-if="studentStep === 1"
              :disabled="studentCode == null || studentCode.length !== 6"
            >
              <span class="pi pi-sign-in text-xl"></span>
              <span class="pl-2">验证</span>
            </Button>
            <Button
              @click="resetStudentPswd"
              v-if="studentStep === 2"
              :disabled="
                pswd == null || pswdConfirm == null || pswd !== pswdConfirm
              "
            >
              <span class="pi pi-key text-xl"></span>
              <span class="pl-2">重置密码</span>
            </Button>
          </span>
        </div>
      </TabPanel>

      <TabPanel>
        <template #header>
          <span class="pi pi-graduation-cap text-xl"></span>
          <span class="pl-2">教师找回密码</span>
        </template>
        <div class="flex flex-col items-center gap-4">
          <div class="max-w-64 font-bold text-gray-400">
            注：因为未知教师邮箱规律，教师需主动提供邮箱。
          </div>
          <InputText
            placeholder="教师 ID"
            v-model:model-value="uid"
            :readonly="teacherStep !== 0"
            :disabled="teacherStep !== 0"
          />
          <InputText
            placeholder="邮箱"
            v-model:model-value="email"
            :readonly="teacherStep !== 0"
            :disabled="teacherStep !== 0"
          />
          <InputText
            placeholder="验证码"
            v-model:model-value="teacherCode"
            v-if="teacherStep === 1"
          />
          <Password
            placeholder="新密码"
            v-if="teacherStep === 2"
            v-model="pswd"
          />
          <Password
            placeholder="确认密码"
            v-if="teacherStep === 2"
            v-model="pswdConfirm"
          />
          <Button
            @click="teacherRecover"
            v-if="teacherStep === 0"
            :disabled="
              uid == null || uid.length == 0 || !isValidMail(email ?? '')
            "
          >
            <span class="pi pi-sign-in text-xl"></span>
            <span class="pl-2">发送邮件</span>
          </Button>
          <Button
            @click="teacherVerify"
            v-if="teacherStep === 1"
            :disabled="teacherCode == null || teacherCode.length !== 6"
          >
            <span class="pi pi-sign-in text-xl"></span>
            <span class="pl-2">验证</span>
          </Button>
          <Button
            @click="resetTeacherPswd"
            v-if="teacherStep === 2"
            :disabled="
              pswd == null || pswdConfirm == null || pswd !== pswdConfirm
            "
          >
            <span class="pi pi-key text-xl"></span>
            <span class="pl-2">重置密码</span>
          </Button>
        </div>
      </TabPanel>
    </TabView>

    <button
      class="flex h-10 w-40 items-center justify-center rounded-lg bg-gray-100 font-bold text-blue-600 duration-75 ease-out hover:w-52 hover:bg-white"
      @click="navigateTo('/welcome')"
    >
      返回登录
    </button>
  </div>
</template>

<script lang="ts" setup>
import type InputText from "primevue/inputtext";

const cfg = useRuntimeConfig();
const apiServer = cfg.public.apiServerBase;
const toasts = useToast();

const pswd = ref<string>();
const pswdConfirm = ref<string>();

const uid = ref<string>();
const studentCode = ref<string>();
const studentStep = ref<number>(0);

async function studentRecover() {
  const { error } = useFetch(`${apiServer}/email/student/${uid.value}`, {
    method: "POST",
  });

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "发送邮件失败",
      detail: error.value.message,
      life: 5000,
    });
  } else {
    toasts.add({
      severity: "success",
      summary: "发送邮件成功",
      detail: "请查看您的邮箱",
      life: 5000,
    });
    studentStep.value = 1;
  }
}

async function studentVerify() {
  const { error } = useFetch(`${apiServer}/email/verify`, {
    method: "POST",
    body: JSON.stringify({
      id: uid.value,
      code: studentCode.value,
    }),
  });

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "验证失败",
      detail: error.value.message,
      life: 5000,
    });
  } else {
    toasts.add({
      severity: "success",
      summary: "验证成功",
      detail: "请查看您的邮箱",
      life: 5000,
    });
    studentStep.value = 2;
  }
}

async function resetStudentPswd() {
  const { error } = useFetch(`${apiServer}/student/${uid.value}/password`, {
    method: "PUT",
    body: JSON.stringify({
      pswd: pswd.value,
    }),
  });

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "重置密码失败",
      detail: error.value.message,
      life: 5000,
    });
  } else {
    toasts.add({
      severity: "success",
      summary: "重置密码成功",
      detail: "请重新登录",
      life: 5000,
    });
    await navigateTo("/welcome");
  }
}

const email = ref<string>();
const teacherCode = ref<string>();
const teacherStep = ref<number>(0);

async function teacherRecover() {
  const { error } = useFetch(`${apiServer}/email/teacher/${uid.value}`, {
    method: "POST",
    body: JSON.stringify({
      email: email.value,
    }),
  });

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "发送邮件失败",
      detail: error.value.message,
      life: 5000,
    });
  } else {
    toasts.add({
      severity: "success",
      summary: "发送邮件成功",
      detail: "请查看您的邮箱",
      life: 5000,
    });
    teacherStep.value = 1;
  }
}

async function teacherVerify() {
  const { error } = useFetch(`${apiServer}/email/verify`, {
    method: "POST",
    body: JSON.stringify({
      id: uid.value,
      code: teacherCode.value,
    }),
  });

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "验证失败",
      detail: error.value.message,
      life: 5000,
    });
  } else {
    toasts.add({
      severity: "success",
      summary: "验证成功",
      detail: "请查看您的邮箱",
      life: 5000,
    });
    teacherStep.value = 2;
  }
}

async function resetTeacherPswd() {
  const { error } = useFetch(`${apiServer}/teacher/${uid.value}/password`, {
    method: "PUT",
    body: JSON.stringify({
      pswd: pswd.value,
    }),
  });

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "重置密码失败",
      detail: error.value.message,
      life: 5000,
    });
  } else {
    toasts.add({
      severity: "success",
      summary: "重置密码成功",
      detail: "请重新登录",
      life: 5000,
    });
    await navigateTo("/welcome");
  }
}

function isValidMail(mail: string) {
  return /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/.test(mail);
}
</script>
