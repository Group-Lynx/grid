<template>
  <div class="flex h-full">
    <!-- Student Todo List -->
    <div class="flex basis-1/2 flex-col items-center overflow-auto bg-lime-400">
      <div
        v-for="todo in todos"
        class="my-3 flex w-3/4 items-center justify-between bg-cyan-300"
      >
        <!-- Student Todo Item -->
        <button class="flex grow bg-violet-400" @click="viewing = todo">
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
    <div class="basis-1/2 overflow-auto bg-yellow-400">
      <div v-if="viewing" class="flex flex-col items-center">
        <div class="flex w-full items-center justify-between bg-sky-400">
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
