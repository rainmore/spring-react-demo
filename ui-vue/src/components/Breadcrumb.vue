<script setup lang="ts">

interface Link {
  name: string,
  url?: string
}

interface Props {
  links: Link[]
}

const props = withDefaults(defineProps<Props>(), {
  links: []
})

</script>

<template>
  <nav className="breadcrumb" aria-label="breadcrumbs" v-if="props.links.length > 0">
    <ul>
      <template v-for="(link, index) in props.links" :key="`link-${index}`">
        <li v-if="index == props.links.length - 1" class="is-active">
          <a href="#" aria-current="page">
            {{ link.name }}
          </a>
        </li>
        <li v-else="index == props.links.length - 1">
          <a :href="link.url">{{ link.name }}</a>
        </li>
      </template>
    </ul>
  </nav>
</template>