export class textfield {
  label!: string
  val!: string
  required!: boolean

  constructor (_label: string, _value = '', _required = false) {
    this.label = _label
    this.val = _value
    this.required = _required
  }

  public update = (_value: string): void => {
    this.val = _value
  }
}
