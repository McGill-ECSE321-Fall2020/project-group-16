export default {
  name: "Address",
  props: ['title', "inputDisabled", "address"],
  data() {
    return {
      streetAddress: '',
      streetAddress2: '',
      postalCode: '',
      city: 'Montreal',
      province: 'QB',
      country: 'Canada'
    }
  }
};