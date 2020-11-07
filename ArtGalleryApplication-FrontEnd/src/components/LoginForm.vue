<template>
  <div id="login-form">
    <label for="username">Username</label><br />
    <input type="text" name="username" id="username" /><br />
    <label for="password">Password</label><br />
    <input type="password" name="password" id="password" />
  </div>
</template>
<script>
export default {
  name: "SignUpForm",
};
</script>
<style scoped>
input {
  border: none;
  border-radius: 15px;
  padding: 5px;
  width: 100%;
}
</style>