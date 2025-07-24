#!/usr/bin/env -S uv run --script
# /// script
# requires-python = ">=3.11"
# dependencies = [
#     "python-dotenv",
# ]
# ///

import argparse
import json
import os
import sys
import subprocess
import random
from pathlib import Path

try:
    from dotenv import load_dotenv
    load_dotenv()
except ImportError:
    pass  # dotenv는 선택사항


def get_tts_script_path():
    """
    사용 가능한 API 키를 기반으로 사용할 TTS 스크립트를 결정합니다.
    우선순위: ElevenLabs > OpenAI > pyttsx3
    """
    # 현재 스크립트 디렉토리를 가져오고 utils/tts 경로 구성
    script_dir = Path(__file__).parent
    tts_dir = script_dir / "utils" / "tts"

    # ElevenLabs API 키 확인 (최우선)
    if os.getenv('ELEVENLABS_API_KEY'):
        elevenlabs_script = tts_dir / "elevenlabs_tts.py"
        if elevenlabs_script.exists():
            return str(elevenlabs_script)

    # OpenAI API 키 확인 (두 번째 우선순위)
    if os.getenv('OPENAI_API_KEY'):
        openai_script = tts_dir / "openai_tts.py"
        if openai_script.exists():
            return str(openai_script)

    # pyttsx3으로 대체 (API 키 불필요)
    pyttsx3_script = tts_dir / "pyttsx3_tts.py"
    if pyttsx3_script.exists():
        return str(pyttsx3_script)

    return None


def announce_notification():
    """에이전트가 사용자 입력이 필요하다고 알립니다."""
    try:
        tts_script = get_tts_script_path()
        if not tts_script:
            return  # 사용 가능한 TTS 스크립트 없음

        # 엔지니어 이름이 있는 경우 가져오기
        engineer_name = os.getenv('ENGINEER_NAME', '').strip()

        # 30% 확률로 이름을 포함한 알림 메시지 생성
        if engineer_name and random.random() < 0.3:
            notification_message = f"{engineer_name}님, 에이전트가 입력을 기다리고 있습니다"
        else:
            notification_message = "에이전트가 입력을 기다리고 있습니다"

        # 알림 메시지로 TTS 스크립트 호출
        subprocess.run([
            "uv", "run", tts_script, notification_message
        ],
        capture_output=True,  # 출력 숨김
        timeout=10  # 10초 타임아웃
        )

    except (subprocess.TimeoutExpired, subprocess.SubprocessError, FileNotFoundError):
        # TTS에서 문제가 발생하면 조용히 실패
        pass
    except Exception:
        # 기타 오류에 대해서도 조용히 실패
        pass


def main():
    try:
        # 명령줄 인수 파싱
        parser = argparse.ArgumentParser()
        parser.add_argument('--notify', action='store_true', help='TTS 알림 활성화')
        args = parser.parse_args()

        # stdin에서 JSON 입력 읽기
        input_data = json.loads(sys.stdin.read())

        # 로그 디렉토리가 존재하는지 확인
        import os
        log_dir = os.path.join(os.getcwd(), 'logs')
        os.makedirs(log_dir, exist_ok=True)
        log_file = os.path.join(log_dir, 'notification.json')

        # 기존 로그 데이터 읽기 또는 빈 리스트 초기화
        if os.path.exists(log_file):
            with open(log_file, 'r') as f:
                try:
                    log_data = json.load(f)
                except (json.JSONDecodeError, ValueError):
                    log_data = []
        else:
            log_data = []

        # 새 데이터 추가
        log_data.append(input_data)

        # 포맷팅하여 파일에 다시 쓰기
        with open(log_file, 'w') as f:
            json.dump(log_data, f, indent=2)

        # --notify 플래그가 설정된 경우에만 TTS를 통해 알림 발표
        # 일반적인 "Claude is waiting for your input" 메시지는 TTS 건너뛰기
        if args.notify and input_data.get('message') != 'Claude is waiting for your input':
            announce_notification()

        sys.exit(0)

    except json.JSONDecodeError:
        # JSON 디코드 오류를 우아하게 처리
        sys.exit(0)
    except Exception:
        # 기타 오류를 우아하게 처리
        sys.exit(0)

if __name__ == '__main__':
    main()