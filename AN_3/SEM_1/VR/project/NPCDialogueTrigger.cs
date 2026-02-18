using UnityEngine;
using System.Collections;

public class NPCDialogueTrigger : MonoBehaviour
{
    [Header("Detection")]
    public Transform playerCamera;
    public float triggerDistance = 2f;

    [Header("Dialogue")]
    [TextArea]
    public string[] dialogueLines;
    public float timePerLine = 5f;

    private bool hasStarted = false;

    void Start()
    {
        if (playerCamera == null && Camera.main != null)
            playerCamera = Camera.main.transform;
    }

    void Update()
    {
        if (hasStarted || playerCamera == null)
            return;

        float dist = Vector3.Distance(playerCamera.position, transform.position);
        if (dist <= triggerDistance)
        {
            hasStarted = true;
            SpeechManager.Instance.EnqueueDialogue(PlayDialogue());
        }
    }

    IEnumerator PlayDialogue()
    {
        foreach (string line in dialogueLines)
        {
            yield return SpeechManager.Instance.ShowLine(line, timePerLine);
        }
    }

}