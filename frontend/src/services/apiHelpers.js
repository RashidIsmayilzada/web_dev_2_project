export const DEFAULT_COLLECTION_SIZE = 100

export const withCollectionDefaults = (params = {}) => ({
  page: 0,
  size: DEFAULT_COLLECTION_SIZE,
  ...params
})

export const unwrapCollectionResponse = (data) => {
  if (Array.isArray(data)) return data
  return data?.content || []
}
