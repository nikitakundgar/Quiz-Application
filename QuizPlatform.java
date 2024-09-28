import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizPlatform {
    private List<Quiz> quizzes;
    private List<User> users;
    private User currentUser;

    public QuizPlatform() {
        quizzes = new ArrayList<>();
        users = new ArrayList<>();
        // Add some test users
        users.add(new User("instructor", "password"));
        users.add(new User("student", "password"));
    }

    public void login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                return;
            }
        }
        System.out.println("Invalid credentials.");
    }

    public void createQuiz(String title) {
        if (currentUser != null && currentUser.getUsername().equals("instructor")) {
            Quiz quiz = new Quiz(title);
            quizzes.add(quiz);
            System.out.println("Quiz created.");
        } else {
            System.out.println("Only instructors can create quizzes.");
        }
    }

    public void addQuestionToQuiz(String quizTitle, String questionText, String[] options, int correctOptionIndex) {
        if (currentUser != null && currentUser.getUsername().equals("instructor")) {
            for (Quiz quiz : quizzes) {
                if (quiz.getTitle().equals(quizTitle)) {
                    Question question = new Question(questionText, options, correctOptionIndex);
                    quiz.addQuestion(question);
                    System.out.println("Question added.");
                    return;
                }
            }
            System.out.println("Quiz not found.");
        } else {
            System.out.println("Only instructors can add questions.");
        }
    }

    public void takeQuiz(String quizTitle) {
        if (currentUser != null && currentUser.getUsername().equals("student")) {
            for (Quiz quiz : quizzes) {
                if (quiz.getTitle().equals(quizTitle)) {
                    List<Question> questions = quiz.getQuestions();
                    int correctAnswersCount = 0;
                    Scanner scanner = new Scanner(System.in);

                    for (Question question : questions) {
                        System.out.println(question.getQuestionText());
                        String[] options = question.getOptions();
                        for (int i = 0; i < options.length; i++) {
                            System.out.println((i + 1) + ". " + options[i]);
                        }
                        System.out.print("Select an option: ");
                        int selectedOptionIndex = scanner.nextInt() - 1;
                        if (question.isAnswerCorrect(selectedOptionIndex)) {
                            correctAnswersCount++;
                        }
                    }

                    QuizResult result = new QuizResult(quiz, correctAnswersCount, questions.size());
                    System.out.println(result);
                    return;
                }
            }
            System.out.println("Quiz not found.");
        } else {
            System.out.println("Only students can take quizzes.");
        }
    }

    public static void main(String[] args) {
        QuizPlatform platform = new QuizPlatform();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Create Quiz (Instructor only)");
            System.out.println("3. Add Question to Quiz (Instructor only)");
            System.out.println("4. Take Quiz (Student only)");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    platform.login(username, password);
                    break;
                case 2:
                    System.out.print("Quiz Title: ");
                    String title = scanner.nextLine();
                    platform.createQuiz(title);
                    break;
                case 3:
                    System.out.print("Quiz Title: ");
                    title = scanner.nextLine();
                    System.out.print("Question Text: ");
                    String questionText = scanner.nextLine();
                    System.out.print("Number of Options: ");
                    int numOptions = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    String[] options = new String[numOptions];
                    for (int i = 0; i < numOptions; i++) {
                        System.out.print("Option " + (i + 1) + ": ");
                        options[i] = scanner.nextLine();
                    }
                    System.out.print("Correct Option Index (1-based): ");
                    int correctOptionIndex = scanner.nextInt() - 1;
                    platform.addQuestionToQuiz(title, questionText, options, correctOptionIndex);
                    break;
                case 4:
                    System.out.print("Quiz Title: ");
                    title = scanner.nextLine();
                    platform.takeQuiz(title);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
