<template>
  <div id="sign-up-form">
    <div class="name">
      <span
        ><label for="first-name">First Name</label><br /><input
          type="text"
          name="first-name"
          id="first-name"
      /></span>
      <span
        ><label for="last-name">Last Name</label><br />
        <input type="text" name="last-name" id="last-name"
      /></span>
    </div>

    <label for="username">Username</label><br />
    <input type="text" name="username" id="username" /><br />
    <label for="email">Email</label><br />
    <input type="email" name="email" id="email" /><br />
    <label for="password">Password</label><br />
    <input type="password" name="password" id="password" /><br />

    <div class="button"><p>Sign Up</p></div>
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
.name {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.button {
  align-self: center;
  padding: 5px;
  border-radius: 10px;
  background-image: linear-gradient(to right, red, orange);
  width: max-content;
  text-align: center;
  color: white;
}
</style>