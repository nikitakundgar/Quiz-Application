public class QuizResult {
    private Quiz quiz;
    private int correctAnswersCount;
    private int totalQuestions;

    public QuizResult(Quiz quiz, int correctAnswersCount, int totalQuestions) {
        this.quiz = quiz;
        this.correctAnswersCount = correctAnswersCount;
        this.totalQuestions = totalQuestions;
    }

    public double getScorePercentage() {
        return (double) correctAnswersCount / totalQuestions * 100;
    }

    @Override
    public String toString() {
        return "Quiz: " + quiz.getTitle() + "\n" +
               "Score: " + getScorePercentage() + "%";
    }
}
