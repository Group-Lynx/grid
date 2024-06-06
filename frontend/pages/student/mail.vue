<template>
  <div class="flex h-full">
    <!-- Student Mail List -->
    <div
      class="flex basis-1/2 flex-col items-center overflow-auto bg-blue-200/30"
    >
      <div
        v-for="mail in mails"
        class="my-3 flex w-3/4 items-center justify-between"
      >
        <!-- Student Mail Item -->
        <button
          class="flex grow rounded-md bg-blue-300 duration-75 ease-out hover:-translate-y-0.5 hover:shadow-lg"
          @click="viewing = mail"
        >
          <p class="m-4 text-xl font-bold">
            {{ mail.title }}
          </p>
        </button>
      </div>
    </div>

    <!-- Student Mail Detail -->
    <div class="basis-1/2 overflow-auto" :class="viewing ? '' : 'bg-slate-200'">
      <div v-if="viewing" class="flex flex-col items-center">
        <div class="flex w-full items-center justify-between bg-blue-200">
          <p class="m-4 text-2xl font-bold">{{ viewing.title }}</p>
          <div>
            <Button class="m-4" @click="showAlert">标记已读</Button>
            <Button class="m-4" @click="showAlert">转为待办</Button>
          </div>
        </div>

        <p class="m-4">{{ viewing.detail }}</p>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  layout: "student",
});

const viewing = ref<Mail | null>(null);
const cfg = useRuntimeConfig();
const apiServer = cfg.public.apiServerBase;
const studentId = useCookie<string | null>("studentId");
const toasts = useToast();

const mails = useState<Mail[]>("mails");
{
  const { data, error } = await useFetch<StuMailResp[]>(
    `${apiServer}/student/${studentId.value}/mail`,
  );

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "未知错误",
      detail: "尝试刷新页面或重新登录",
      life: 5000,
    });
  } else {
    mails.value = [];
    for (let mail of data.value!) {
      mails.value.push({
        id: mail.mailId,
        title: mail.title,
        detail: null,
      });
    }
  }
}

watch(viewing, async (_, newViewing) => {
  const { data, error } = await useFetch<StuMailDetailResp>(
    `${apiServer}/student/${studentId.value}/mail/${newViewing}`,
  );

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "未知错误",
      detail: "尝试刷新页面或重新登录",
      life: 5000,
    });
  } else {
    // update the fields of the mail `newViewing`
    mails.value = mails.value.map((mail) => {
      if (mail.id === newViewing?.id) {
        mail.detail = data.value!.detail;
      }
      return mail;
    });
  }
});

function showAlert() {
  alert("FIXME");
}
</script>

<style></style>
