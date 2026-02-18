using UnityEngine;
using TMPro;
using System.Collections;
using System.Collections.Generic;

public class SpeechManager : MonoBehaviour
{
    public static SpeechManager Instance;

    public TextMeshProUGUI speechText;

    private Queue<IEnumerator> speechQueue = new Queue<IEnumerator>();
    private bool isPlaying = false;

    void Awake()
    {
        Instance = this;
        Hide();
    }

    public void EnqueueDialogue(IEnumerator dialogueRoutine)
    {
        speechQueue.Enqueue(dialogueRoutine);

        if (!isPlaying)
            StartCoroutine(PlayQueue());
    }

    IEnumerator PlayQueue()
    {
        isPlaying = true;

        while (speechQueue.Count > 0)
        {
            yield return StartCoroutine(speechQueue.Dequeue());
        }

        isPlaying = false;
        Hide();
    }

    public IEnumerator ShowLine(string message, float duration)
    {
        speechText.text = message;
        speechText.gameObject.SetActive(true);
        yield return new WaitForSeconds(duration);
    }

    void Hide()
    {
        speechText.gameObject.SetActive(false);
    }
}