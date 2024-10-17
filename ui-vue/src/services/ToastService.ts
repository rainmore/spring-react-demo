import { useToast } from 'vue-toast-notification'

class ToastService {
  private toast = useToast()

  clear(): void {
    this.toast.clear()
  }

  error(msg: string): void {
    this.toast.error(msg)
  }

  info(msg: string): void {
    this.toast.info(msg)
  }

  success(msg: string): void {
    this.toast.success(msg)
  }

  warn(msg: string): void {
    this.toast.warning(msg)
  }
}

export const toastService = new ToastService()
