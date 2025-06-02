export interface Page<T = unknown> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  sort: string[];
}
