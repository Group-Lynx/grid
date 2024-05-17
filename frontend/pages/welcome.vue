<!-- Login & Signup Logic -->
<template>
  <div class="flex h-screen w-screen items-center justify-center bg-blue-200">
    <TabView>
      <TabPanel header="登录">
        <div class="flex flex-col items-center gap-4">
          <InputText placeholder="用户名" />
          <Password :feedback="false" placeholder="密码" />
          <Button>登录</Button>
        </div>
      </TabPanel>
      <TabPanel header="注册">
        <div class="flex flex-col items-center gap-4">
          <InputText placeholder="用户名" />
          <Password :feedback="false" placeholder="密码" />
          <Password :feedback="false" placeholder="确认密码" />
          <Button>注册</Button>
        </div>
      </TabPanel>
    </TabView>
  </div>
</template>

<script lang="ts" setup>
const action = ref<"login" | "signup">("login");
</script>

<style></style>
