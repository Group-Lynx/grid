<template>
  <div class="flex h-full w-full flex-col">
    <div class="flex-grow bg-lime-400">死线任务区</div>
    <div class="grid h-16 grid-cols-7 grid-rows-1 bg-red-400">
      <div>1</div>
      <div>2</div>
      <div>3</div>
      <div>4</div>
      <div>5</div>
      <div>6</div>
      <div>7</div>
    </div>
    <div
      class="grid basis-3/5 grid-flow-col grid-cols-7 grid-rows-5 bg-yellow-400"
    >
      <div>1</div>
      <div>2</div>
      <div>3</div>
      <div>4</div>
      <div>5</div>
      <div>6</div>
      <div>7</div>
      <div>9</div>
      <div>10</div>
      <div>11</div>
      <div>12</div>
    </div>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  layout: "student",
});

const events = useState<StuEvent[]>("events");
</script>

<style></style>
