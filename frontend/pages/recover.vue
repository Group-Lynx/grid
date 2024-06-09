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
            placeholder="用户 ID"
            :readonly="studentStep !== 0"
            :disabled="studentStep !== 0"
            v-model:model-value="uid"
          />
          <InputNumber
            placeholder="验证码"
            v-model:model-value="studentCode"
            v-if="studentStep === 1"
          />
          <span class="flex gap-4">
            <Button @click="studentRecover" v-if="studentStep === 0">
              <span class="pi pi-envelope text-xl"></span>
              <span class="pl-2">发送邮件</span>
            </Button>
            <Button @click="studentVerify" v-if="studentStep === 1">
              <span class="pi pi-sign-in text-xl"></span>
              <span class="pl-2">验证</span>
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
          <InputText placeholder="邮箱" v-model:model-value="email" />
          <Button @click="teacherRecover">
            <span class="pi pi-sign-in text-xl"></span>
            <span class="pl-2">发送邮件</span>
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
import InputNumber from "primevue/inputnumber";

const cfg = useRuntimeConfig();
const apiServer = cfg.public.apiServerBase;
const toasts = useToast();

const uid = ref<string>();
const studentCode = ref<number>();
const studentStep = ref<number>(0);

async function studentRecover() {
  const { error } = useFetch(`${apiServer}/email/student/${uid}`, {
    method: "POST",
  });

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "发送邮件失败",
      detail: error.value.message,
    });
  } else {
    toasts.add({
      severity: "success",
      summary: "发送邮件成功",
      detail: "请查看您的邮箱",
    });
    studentStep.value = 1;
  }
}

const email = ref<string>();
const teacherCode = ref<number>();

async function teacherRecover() {
  alert("教师找回密码");
}
</script>
