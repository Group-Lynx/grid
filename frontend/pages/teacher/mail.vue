<template>
  <div class="flex h-full w-full">
    <!-- Mail List -->
    <div
      class="flex basis-1/2 flex-col items-center overflow-auto bg-blue-200/30"
    >
      <!-- A Mail Item -->
      <div
        class="my-3 flex w-3/4 items-center justify-between rounded-md bg-blue-300 duration-75 ease-out hover:-translate-y-0.5 hover:shadow-lg"
      >
        <button
          class="flex grow items-center justify-between"
          @click="addMailDialogVisible = true"
        >
          <p class="m-4 text-xl font-bold">添加通知</p>
          <i class="pi pi-window-maximize mr-4 text-2xl"></i>
        </button>
      </div>

      <div
        v-for="mail in mails"
        class="my-3 flex w-3/4 items-center justify-between rounded-md bg-blue-300 duration-75 ease-out hover:-translate-y-0.5 hover:shadow-lg"
      >
        <button class="flex grow" @click="viewing = mail">
          <p class="m-4 text-xl font-bold">
            {{ mail.title }}
          </p>
        </button>
      </div>
    </div>

    <!-- Mail Detail -->
    <div
      class="h-full basis-1/2 overflow-auto"
      :class="viewing ? '' : 'bg-slate-200'"
    >
      <div v-if="viewing" class="flex flex-col items-center">
        <div class="flex w-full items-center justify-between bg-blue-300">
          <p class="m-4 text-2xl font-bold">{{ viewing.title }}</p>
          <div>
            <Button class="m-2" @click="beginEditMail">编辑</Button>
            <Button class="m-2" @click="publishMail(viewing.id)">分发</Button>
          </div>
        </div>

        <p class="w-full items-start p-4">{{ viewing.detail }}</p>
      </div>
    </div>

    <Dialog v-model:visible="addMailDialogVisible" header="添加通知" modal>
      <div class="flex flex-col items-center gap-4">
        <div class="flex items-center gap-2">
          <label for="addMailTitleId">通知标题</label>
          <InputText id="addMailTitleId" v-model:model-value="addMailTitle" />
        </div>
        <div class="flex items-center gap-2">
          <label for="addMailDetailId">通知内容</label>
          <Textarea id="addMailDetailId" v-model:model-value="addMailDetail" />
        </div>
        <Button
          @click="addMail"
          :disabled="addMailTitle == null || addMailDetail == null"
          >确认添加</Button
        >
      </div>
    </Dialog>

    <Dialog v-model:visible="editMailDialogVisible" header="编辑通知" modal>
      <div class="flex flex-col items-center gap-4">
        <div class="flex items-center gap-2">
          <label for="editMailTitleId">通知标题</label>
          <InputText id="editMailTitleId" v-model:model-value="editMailTitle" />
        </div>
        <div class="flex items-center gap-2">
          <label for="editMailDetailId">通知内容</label>
          <Textarea
            id="editMailDetailId"
            v-model:model-value="editMailDetail"
          />
        </div>
        <Button
          @click="editMail(viewing!.id)"
          :disabled="editMailTitle == null || editMailDetail == null"
          >确认编辑</Button
        >
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

const mails = useState<ClassMail[]>("classMails");
const selectedClass = useState<Class>("selectedClass");

// LYN: Get Mail List
async function getMailList(newClass: Class) {
  const { data, error } = await useFetch<ClaMailResp[]>(
    `${apiServer}/class/${newClass.id}/mail`,
  );

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "获取通知列表失败",
      detail: "尝试刷新页面或重新登录",
      life: 5000,
    });
  } else {
    mails.value = [];
    for (let mail of data.value!) {
      mails.value.push({
        id: mail.mailId,
        title: mail.title,
      });
    }
  }
}
watch(selectedClass, async (newClass, _) => {
  await getMailList(newClass);
});
if (selectedClass.value != null) {
  await getMailList(selectedClass.value);
}

// LYN: Viewing Mail
const viewing = ref<ClassMail | null>(null);
watch(viewing, async (newMail) => {
  if (newMail != null) {
    const { data, error } = await useFetch<MailDetailResp>(
      `${apiServer}/class/${selectedClass.value.id}/mail/${newMail.id}`,
    );

    if (error.value != null) {
      toasts.add({
        severity: "error",
        summary: "获取通知详情失败",
        detail: "尝试刷新页面或重新登录",
      });
    } else {
      viewing.value!.draft = data.value!.draft;
      viewing.value!.detail = data.value!.detail;
    }
  }
});

// LYN: Add Mail
const addMailDialogVisible = ref<boolean>(false);
const addMailTitle = ref<string>();
const addMailDetail = ref<string>();
async function addMail() {
  const { error } = await useFetch(
    `${apiServer}/class/${selectedClass.value.id}/mail`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        title: addMailTitle.value,
        detail: addMailDetail.value,
      }),
    },
  );

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "添加通知失败",
      detail: "尝试刷新页面或重新登录",
      life: 5000,
    });
  } else {
    if (selectedClass.value != null) {
      await getMailList(selectedClass.value);
    }
    addMailDialogVisible.value = false;
  }
}

// LYN: publish mail
async function publishMail(mailId: string) {
  const { error } = await useFetch(
    `${apiServer}/class/${selectedClass.value.id}/mail/${mailId}`,
    {
      method: "POST",
    },
  );

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "发布通知失败",
      detail: "尝试刷新页面或重新登录",
      life: 5000,
    });
  } else {
    if (selectedClass.value != null) {
      await getMailList(selectedClass.value);
    }
  }
}

// LYN: edit mail
const editMailDialogVisible = ref<boolean>(false);
const editMailTitle = ref<string>();
const editMailDetail = ref<string>();

function beginEditMail() {
  if (viewing.value != null) {
    editMailDialogVisible.value = true;
    editMailTitle.value = viewing.value.title;
    editMailDetail.value = viewing.value.detail;
  }
}

async function editMail(mailId: string) {
  const { error } = await useFetch(
    `${apiServer}/class/${selectedClass.value.id}/mail/${mailId}`,
    {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        title: editMailTitle.value,
        detail: editMailDetail.value,
      }),
    },
  );

  if (error.value != null) {
    toasts.add({
      severity: "error",
      summary: "编辑通知失败",
      detail: "尝试刷新页面或重新登录",
      life: 5000,
    });
  } else {
    if (selectedClass.value != null) {
      await getMailList(selectedClass.value);
      viewing.value = null;
      editMailDialogVisible.value = false;
      editMailTitle.value = undefined;
      editMailDetail.value = undefined;
    }
  }
}
</script>
