
type TextAreaProps = {
  label,
  id,
  errors,
  register,
  required,
  message,
  className,
  placeholder
}

const TextArea = ({ label, id, errors, register, required, message, className, placeholder }: TextAreaProps) => {
  return (
    <div className="flex flex-col gap-1 w-full">
      <label htmlFor="id" className={`${className ? className : ""} font-semibold text-sm text-slate-800`}>
        {label}
      </label>
      <textarea rows={5} placeholder={placeholder} className={`px-4 py-2 w-full border outline-none bg-transparent text-slate-800 rounded-md ${errors["description"]?.message ? "border-red-500 " : "border-slate-700"}`} {...register("description", {
        required: { value: required, message: message },
      })} />

      {errors[id]?.message && (<p className="text-sm font-semibold text-red-600 mt-0">
        {errors[id]?.message}
      </p>)}
    </div >
  )
};

export default TextArea;

