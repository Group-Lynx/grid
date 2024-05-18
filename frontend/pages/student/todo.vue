<template>
  <div class="flex h-full">
    <!-- Student Todo List -->
    <div
      class="flex basis-1/2 flex-col items-center overflow-auto bg-blue-200/30"
    >
      <div
        v-for="todo in todos"
        class="my-3 flex w-3/4 items-center justify-between rounded-md bg-blue-300 duration-75 ease-out hover:-translate-y-0.5 hover:shadow-lg"
      >
        <!-- Student Todo Item -->
        <button class="flex grow" @click="viewing = todo">
          <p class="m-4 text-xl font-bold">
            {{ todo.title }}
            <i v-if="todo.display" class="pi pi-eye"></i>
          </p>
        </button>
        <span class="m-4">
          <Checkbox v-model="todo.done" binary />
        </span>
      </div>
    </div>

    <!-- Student Todo Detail -->
    <div class="basis-1/2 overflow-auto" :class="viewing ? '' : 'bg-slate-200'">
      <div v-if="viewing" class="flex flex-col items-center">
        <div class="flex w-full items-center justify-between bg-blue-200">
          <p class="m-4 text-2xl font-bold">{{ viewing.title }}</p>
          <div>
            <Button class="m-4" @click="showAlert">完成任务</Button>
            <Button class="m-4" @click="showAlert">加入日程</Button>
          </div>
        </div>

        <p class="m-4">{{ viewing.due }}</p>
        <p class="m-4">{{ viewing.detail }}</p>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  layout: "student",
});

const todos = useState<Todo[]>("todos");
const viewing = ref<Todo | null>(null);

function showAlert() {
  alert("FIXME");
}
</script>

<style></style>
