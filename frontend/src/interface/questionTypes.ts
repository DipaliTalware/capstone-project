export interface Question {
  _id: string;
  courseId: string;
  text: string;
  options: string[];
  correctAnswer: string;
  _class: string;
}
