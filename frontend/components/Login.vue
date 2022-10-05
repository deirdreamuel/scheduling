<template>
  <v-card outlined max-width="500px">
    <v-card-title>LOG IN</v-card-title>
    <v-list-item>
      <v-list-item-content>
        <Textfield
          :label="email.label"
          :value="email.val"
          @update="email.update"
        ></Textfield>
        <Textfield
          :show="false"
          :showIcon="true"
          :label="password.label"
          :value="password.val"
          @update="password.update"
        ></Textfield>
        <v-btn block text @click="login" :color="!(message)? 'normal': 'error'"> LOG IN </v-btn>
        <p class="message d-flex justify-center pt-1">
            {{ message }}
        </p>
      </v-list-item-content>
    </v-list-item>
  </v-card>
</template>

<script>
import { defineComponent, useContext, ref, useRouter } from "@nuxtjs/composition-api";

import Textfield from "~/components/fields/textfield.vue";
import { textfield } from "~/models/textfield";

export default defineComponent({
  name: "Login",
  components: { Textfield },
  setup() {
    let email = new textfield("email", "deirsre@gmail.com");
    let password = new textfield("password", "hello");
    const { $axios } = useContext();
    const router = useRouter();
    
    const message = ref('');
    const login = () => {
      const FormData = require("form-data");
      const form = new FormData();
      form.append("email", email.val);
      form.append("password", password.val);

      $axios.$post("/login", form, {
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
        })
        .then((resp) => {
          console.log("response");
          console.log(resp);
            router.push('/loggedin')
        })
        .catch((error) => {
          console.log(error.response);
          message.value = error.response.data;
        });
    };

    return {
      email,
      password,
      message,
      login,
    };
  },
});
</script>

<style>
.message {
    color: red
}
</style>