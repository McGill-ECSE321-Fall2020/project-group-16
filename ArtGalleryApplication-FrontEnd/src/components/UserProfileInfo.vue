<template>
  <div class="container">
    <h2>It Worked!</h2>
  </div>
</template>

<script>
export default {
  name: "UserProfileInfo",
  props: {
    username: String,
    firstName: String,
    lastName: String,
    email: String,
  },
};
</script>

<style>
</style>