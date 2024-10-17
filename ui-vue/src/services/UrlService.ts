import { useRoute, useRouter } from 'vue-router'
import { AppRoutePaths } from '../AppRoutes'

export class UrlService {
  private route = useRoute()
  private router = useRouter()

  getBasePath(): string {
    return this.route.path
  }

  redirect(path: AppRoutePaths): void {
    this.router.push(path)
  }

  getSearchParameter(): string {
    return window.location.search !== '' ? `?${window.location.search}` : ''
  }

  getURLSearchParams(): URLSearchParams {
    return new URLSearchParams(window.location.search)
  }
}
