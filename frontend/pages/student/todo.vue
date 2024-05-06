<template>
  <div class="flex h-full">
    <!-- Student Todo List -->
    <div class="basis-1/2 bg-lime-400 flex flex-col items-center overflow-auto">
      <div
        v-for="todo in todos"
        class="bg-cyan-300 w-3/4 my-3 flex justify-between items-center"
      >
        <!-- Student Todo Item -->
        <span class="bg-violet-400 grow" @click="selected = todo">
          <p class="font-bold text-xl m-4">{{ todo.title }}</p>
        </span>
        <span class="m-4">
          <Checkbox v-model="todo.done" binary />
        </span>
      </div>
    </div>

    <!-- Student Todo Detail -->
    <div class="basis-1/2 bg-yellow-400">
      <div v-if="selected" class="flex flex-col items-center">
        <p class="font-bold text-2xl m-4">{{ selected.title }}</p>
        <p class="m-4">{{ selected.due }}</p>
        <p class="m-4">{{ selected.detail }}</p>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  layout: "student",
});

type Todo = {
  todoId: string;
  title: string;
  due: string;
  detail: string;
  done: boolean;
};

const todos = ref<Todo[]>([]);
const selected = ref<Todo | null>(null);

// FIXME: Replace with real data
let startDate = new Date("2021-10-01");
for (let i = 0; i < 26; i++) {
  let dueDate = new Date(startDate.getTime() + i * 24 * 60 * 60 * 1000);
  let todo = {
    todoId: String.fromCharCode(65 + i),
    title: String.fromCharCode(65 + i) + " Title",
    due: dueDate.toISOString().split("T")[0],
    detail:
      String.fromCharCode(65 + i) +
      " Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec efficitur nibh ac mauris congue lobortis. Mauris sed metus sem. Fusce consectetur viverra tellus, at semper lorem blandit ut. Aliquam ullamcorper vitae metus a sodales. Nam lorem ante, posuere et nulla sed, feugiat varius lacus. Praesent egestas sodales lorem vulputate semper. Nam vel felis et neque convallis mollis. Sed posuere felis in tortor ullamcorper interdum vel ac sem. Phasellus efficitur ipsum eget risus laoreet placerat. Fusce iaculis scelerisque enim a placerat. Ut consectetur ligula ut leo tincidunt, eu aliquam lorem consectetur. Donec interdum est ante, dignissim posuere erat varius a. Quisque porta volutpat tempus. Morbi rutrum urna vitae nulla faucibus ultricies. Etiam malesuada tortor quis lorem facilisis, quis molestie odio vulputate.",
    done: Math.random() < 0.5,
  };
  todos.value.push(todo);
}
</script>

<style></style>
