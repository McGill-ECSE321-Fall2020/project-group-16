export default {
  name: "Address",
  props: ['title', "inputDisabled", "address"],

  /**
  * declaration of the page's data
  */
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