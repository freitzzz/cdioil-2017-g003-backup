typedef struct{
    int val;
}Binary;

typedef struct{
    int min;
    int max;
}Scale;

typedef struct{
    int time;
    int alternatives;
}MultipleChoice;

typedef struct{
    Binary binaryQuestion;
    Scale scaleQuestion;
    MultipleChoice multipleChoiceQuestion;
}Question;

typedef struct{
    struct Flux* previousFlux;
    Question* currentQuestion;
    struct Flux* nextFlux;
}Flux;