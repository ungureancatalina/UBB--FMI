using UnityEngine;

public class NPCSpotTrigger : MonoBehaviour
{
    [Header("Detection")]
    public float viewAngle = 25f;        
    public float viewDistance = 4f;      

    [Header("References")]
    public Transform playerCamera;      
    public Transform lookTarget;     
    
    [Header("Anxiety Type")]
    public bool isHighAnxietyNPC = false;
    public bool isLowAnxietyNPC = false;

    private AnxietyManager anxietyManager;
    private bool isLooking = false;

    void Start()
    {
        anxietyManager = FindObjectOfType<AnxietyManager>();

        if (playerCamera == null && Camera.main != null)
            playerCamera = Camera.main.transform;
    }

    void Update()
    {
        if (playerCamera == null || anxietyManager == null)
            return;

        bool nowLooking = IsLookingAtNPC();

        if (nowLooking && !isLooking)
        {
            isLooking = true;

            float amount = 1f;

            if (isHighAnxietyNPC)
                amount = 1.5f;
            else if (isLowAnxietyNPC)
                amount = 0.2f;

            anxietyManager.NPCStartedLooking(amount);
        }
        else if (!nowLooking && isLooking)
        {
            isLooking = false;

            float amount = 1f;

            if (isHighAnxietyNPC)
                amount = 1.5f;
            else if (isLowAnxietyNPC)
                amount = 0.2f;

            anxietyManager.NPCStoppedLooking(amount);
        }

    }


    bool IsLookingAtNPC()
    {
        Vector3 dir = lookTarget.position - playerCamera.position;

        float distance = dir.magnitude;
        if (distance > viewDistance)
            return false;

        dir.Normalize();
        float angle = Vector3.Angle(playerCamera.forward, dir);

        return angle <= viewAngle;
    }
}