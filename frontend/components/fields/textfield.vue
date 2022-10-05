<template>
  <div>
    <div class="text-overline">{{ label }}</div>
    <v-text-field
      color="black"
      v-model="fieldValue"
      outlined
      :type="showField ? 'text' : 'password'"
      :append-icon="seeTextIcon"
      :rules="rules"
      @click:append="showField = !showField"
      :dense="dense"
    >
      {{ value }}
    </v-text-field>
  </div>
</template>

<script>
import { defineComponent, ref, watch, computed } from "@vue/composition-api";

export default defineComponent({
  name: "Textfield",
  props: {
    value: {
      default: "",
    },
    label: { default: "" },
    show: { default: true },
    showIcon: { default: false },
    required: { default: false },
    dense: { default: false }
  },

  setup(_props, { emit }) {
    const errorMessage = ref("");

    const rules = [];
    const required = [(value) => !!value || "Required."];

    const seeTextIcon = ref("");
    if (_props.showIcon) {
      seeTextIcon.value = !_props.show ? "mdi-eye" : "mdi-eye-off";
    }

    const showField = ref(_props.show);
    watch(showField, () => {
      seeTextIcon.value = !showField.value ? "mdi-eye" : "mdi-eye-off";
    });

    if (_props.required) {
      rules.extend(required);
    }

    const fieldValue = computed({
      get: () => _props.value,
      set: (value) => {
          emit("update", value)
        }
    });

    return {
     fieldValue,
      errorMessage,
      seeTextIcon,
      rules,
      showField
    };
  },
});
</script>

<style>
.v-text-field--outlined fieldset {
  border-color: black;
}
</style>